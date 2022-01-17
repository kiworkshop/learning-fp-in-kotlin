import { getEq } from "fp-ts/lib/Array";
import { struct } from "fp-ts/lib/Eq";

// type class
export interface Eq<A> {
  readonly equals: (x: A, y: A) => boolean; // type
  // if x equals to y, it returns true
}

// instance example
export const eqNumber: Eq<number> = {
  equals: (x: number, y: number) => x === y,
};

// reflexibility: equals(x,x) = true
// Symmetry: equals(x,y) = equals(y,x)
// Transivity: if both equals(x,y) and equals(y,z) are true, then equals(x,z) is true

export function elem<A>(E: Eq<A>): (a: A, as: Array<A>) => boolean {
  return (a: A, as: A[]) => as.some((el) => E.equals(a, el));
}

// another instance example of Eq

interface Point {
  x: number;
  y: number;
}

export const eqPoint: Eq<Point> = {
  equals: (a: Point, b: Point) => a.x === b.x && a.y === b.y,
};

export const eqPoint2: Eq<Point> = struct({
  x: eqNumber,
  y: eqNumber,
}); // create equal instance based on input structure

export const eqArrayOfPoints: Eq<Array<Point>> = getEq(eqPoint);
// create equal instance of  an array based on a single Point type element.

