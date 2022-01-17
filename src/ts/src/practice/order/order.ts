import { number } from "fp-ts";
import { Eq } from "fp-ts/lib/Eq";
import { fromCompare, contramap, reverse } from "fp-ts/lib/Ord";

type ordering = -1 | 0 | 1;

interface Ord<A> extends Eq<A> {
  readonly compare: (x: A, y: A) => ordering;
}

// number type instance
export const OrdNumber: Ord<number> = {
  equals: (x: number, y: number) => x === y,
  compare: (x: number, y: number) => (x < y ? -1 : x === y ? 0 : 1),
};

// Reflexivity: compare(x,x) = 0
// Assymmetry : compare(x,y) is inverse to compare(y,x)
// Transivity : if compare(x,y) <= 0 , compare(y,z) <= 0 then compare(x,z) <=0

export function min<A>(O: Ord<A>): (x: A, y: A) => A {
  return (x, y) => (O.compare(x, y) === -1 ? x : y);
}

// let's consider a more complex example.
type User = {
  name: string;
  age: number;
};

// This is an instance that satisfies the condition for type class order.
const byAge: Ord<User> = {
  equals: (x, y) => x.age === y.age,
  compare: (x, y) => (x.age < y.age ? -1 : x.age === y.age ? 0 : 1),
};

// but we can simplfy using fromCompare
const byAge2: Ord<User> = fromCompare((first, second) =>
  OrdNumber.compare(first.age, second.age)
);

// or by using contramap...
const byAge3: Ord<User> = contramap((user: User) => user.age)(OrdNumber);

// function that choose younger member
export const getYounger = min(byAge);

export const getOlder = min(reverse(byAge));

function max<A>(O: Ord<A>): (x: A, y: A) => A {
  return min(reverse(O));
}

export const getOlder2 = max(byAge);
