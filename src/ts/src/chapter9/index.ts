import { isNone, none, Option, Some, some } from "fp-ts/lib/Option";

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
      if (m1._tag === "Some" && m2._tag === "Some")
        return some(
          inValue.concat(m1.value, m2.value)
        );
    },
  };
}
