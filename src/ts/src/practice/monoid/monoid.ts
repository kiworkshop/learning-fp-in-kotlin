import { Semigroup } from "fp-ts/lib/Semigroup";
import { concatAll, struct } from "fp-ts/lib/Monoid";
import { Monoid } from "fp-ts/lib/Ordering";

interface Monoid<A> extends Semigroup<A> {
  readonly empty: A;
}

// right identity : For all x of A, concat(x, empty) = x
// left identity: For all x of A, concat(empty, x) = x

/** number 타입의 덧셈 `Monoid` */
export const monoidSum: Monoid<number> = {
  concat: (x, y) => x + y,
  empty: 0,
};

/** number 타입의 곱셈 `Monoid` */
export const monoidProduct: Monoid<number> = {
  concat: (x, y) => x * y,
  empty: 1,
};

export const monoidString: Monoid<string> = {
  concat: (x, y) => x + y,
  empty: "",
};

/** boolean타입의 논리곱 monoid */
export const monoidAll: Monoid<boolean> = {
  concat: (x, y) => x && y,
  empty: true,
};

/** boolean 타입의 논리합 `monoid` */
export const monoidAny: Monoid<boolean> = {
  concat: (x, y) => x || y,
  empty: false,
};

// semigroup 이지만 monoid가 될 수 없는 경우
const semigroupSpace: Monoid<string> = {
  concat: (x, y) => x + " " + y,
  empty: "", // concat(x, empty) != x
};

type Point = {
  x: number;
  y: number;
};

const monoidPoint: Monoid<Point> = struct({
  x: monoidSum,
  y: monoidSum,
});

type Vector = {
  from: Point;
  to: Point;
};

export const monoidVector: Monoid<Vector> = struct({
  from: monoidPoint,
  to: monoidPoint,
});

// concatAll - monoid 에서는 folding이 초기값을 제공해 줄 필요가 없어서 더 간단하다.

concatAll(monoidSum)([1, 2, 3, 4]); // 10
concatAll(monoidProduct)([1, 2, 3, 4]); // 24
concatAll(monoidString)(["a", "b", "c"]); // 'abc'
concatAll(monoidAll)([true, false, true]); // false
concatAll(monoidAny)([true, false, true]); // true
