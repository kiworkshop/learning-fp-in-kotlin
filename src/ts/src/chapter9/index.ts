import { none, Option, some } from "fp-ts/lib/Option";

// monoid type class
export interface Monoid<T> {
  concat: (m1: T, m2: T) => T;
  empty: T;
}

export function maybeMonoid<T>(inValue: Monoid<T>): Monoid<Option<T>> {
  return {
    empty: none,
    concat: (m1, m2) => {
      if (m1._tag === "None") return m2;
      if (m2._tag === "None") return m1;
      else return some(inValue.concat(m1.value, m2.value));
    },
  };
}

export const arrayMonoid: Monoid<Array<any>> = {
  empty: [],
  concat: (m1, m2) => {
    if (m1.length === 0) return m2;
    if (m2.length === 0) return m1;
    else return m1.concat(m2);
  },
};

// ts에서는 enum class가 없는 듯

// export class Nil {
//   public readonly _tag: string;
//   constructor() {
//     this._tag = "Nil";
//   }
// }

// export class Cons<T> {
//   public readonly _tag: string;
//   public readonly head: T;
//   public readonly tail: FunList<T>;
//   constructor() {
//     this._tag = "Cons";
//   }
// }
// export enum FunList<T> {
//   Cons<T>,
//   Nil,
// }

export type Tree<A> = Empty | Node<A>;

export class Empty {
  tag: "empty" = "empty";
  constructor() {}
}
export class Node<A> {
  tag: "node" = "node";
  readonly value: A;
  readonly left: Tree<A>;
  readonly right: Tree<A>;

  constructor(value: A, left: Tree<A>, right: Tree<A>) {
    this.value = value;
    this.left = left;
    this.right = right;
  }
}

function foldLeft<T>(tree: Tree<T>, acc: T, f: (m1: T, m2: T) => T): T {
  if (tree.tag === "empty") return acc;
  else {
    const { left, right, value } = tree;
    const leftAcc = foldLeft(left, acc, f);
    const rootAcc = f(leftAcc, value);
    return foldLeft(right, rootAcc, f);
  }
}


export function FoldMap2<T>(tree: Tree<T>, f: (a: T) => T, m: Monoid<T>): T {
  return foldLeft<T>(tree, m.empty, (m1, m2) => m.concat(m1, f(m2)));
}
