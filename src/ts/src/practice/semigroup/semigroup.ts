// higher concepts of abstraction

import { getApplySemigroup } from "fp-ts/lib/Apply";
import { SemigroupAll } from "fp-ts/lib/boolean";
import { getSemigroup } from "fp-ts/lib/function";
import { Ord, SemigroupProduct, SemigroupSum } from "fp-ts/lib/number";
import { Apply } from "fp-ts/lib/Option";
import { contramap } from "fp-ts/lib/Ord";
import { concatAll, max, min, struct } from "fp-ts/lib/Semigroup";

// * : (x:A, y:A) => A
// e.g.
// number, *
// string, +
// boolean, operator &&

interface Semigroup<A> {
  concat: (x: A, y: A) => A;
}

// number instance
const semigroupSum: Semigroup<number> = {
  concat: (x, y) => x + y,
};

// string instance
const concatString: Semigroup<string> = {
  concat: (x, y) => x + y,
};

// create a semigroup that evaluates min value, from Ord type class
const minSemigroup: Semigroup<number> = min(Ord);

const maxSemigroup: Semigroup<number> = max(Ord);

type Point = {
  x: number;
  y: number;
};

const semigroupPoint: Semigroup<Point> = {
  concat: (p1, p2) => ({
    x: semigroupSum.concat(p1.x, p2.x),
    y: semigroupSum.concat(p1.y, p2.y),
  }),
};

const semigroupPoint2: Semigroup<Point> = struct({
  x: semigroupSum,
  y: semigroupSum,
});

type Vector = {
  from: Point;
  to: Point;
};

const semiGroupVector: Semigroup<Vector> = struct({
  from: semigroupPoint2,
  to: semigroupPoint2,
});

// combinator getSemigroup
export const semigroupPredicate: Semigroup<(p: Point) => boolean> =
  getSemigroup(SemigroupAll)<Point>();
// semigroupAll -> similar to && operator

const isPositiveX = (p: Point): boolean => p.x >= 0;
const isPositiveY = (p: Point): boolean => p.y >= 0;

export const isPositiveXY = semigroupPredicate.concat(isPositiveX, isPositiveY);

//folding
export const sum = concatAll(SemigroupSum);
export const product = concatAll(SemigroupProduct);

// 점점 난해해지고 있다.
export const S = getApplySemigroup(Apply)(SemigroupSum);

// take a real world example. let's say we manage customer info
interface Customer {
  name: string;
  favouriteThings: Array<string>;
  registeredAt: number;
  lastUpdatedAt: number;
  hasMadePurchase: boolean;
}

const semigroupCustomer: Semigroup<Customer> = struct({
  name: max(contramap((s: string) => s.length)(Ord)),
});
