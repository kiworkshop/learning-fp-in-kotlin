import { foldMap } from "fp-ts/Array";
import { concatAll } from "fp-ts/lib/Monoid";
import { none, some } from "fp-ts/lib/Option";
import {
  arrayMonoid,
  Empty,
  FoldMap2,
  maybeMonoid,
  Monoid,
  Node,
  Tree,
} from ".";

describe("chapter 9", function () {
  const MonoidAny: Monoid<boolean> = {
    concat: (x, y) => x || y,
    empty: false,
  };

  const MonoidAll: Monoid<boolean> = {
    concat: (x, y) => x && y,
    empty: true,
  };

  const MonoidSum: Monoid<number> = {
    concat: (x, y) => x + y,
    empty: 0,
  };

  test("1", function () {
    expect(MonoidAny.concat(false, false)).toBeTruthy;
    expect(MonoidAny.concat(true, false)).toBeTruthy;
    expect(MonoidAny.concat(true, true)).toBeTruthy;
  });

  test("2", function () {
    expect(MonoidAll.concat(false, false)).toBeFalsy;
    expect(MonoidAll.concat(true, false)).toBeFalsy;
    expect(MonoidAll.concat(true, true)).toBeTruthy;
  });

  test("3", function () {
    const { concat, empty } = MonoidAny;
    expect(concat(empty, true)).toBeTruthy;
    expect(concat(true, empty)).toBeTruthy;
    expect(concat(concat(true, false), false)).toBeTruthy;
    expect(concat(true, concat(false, false))).toBeTruthy;
  });

  test("4", function () {
    const { concat, empty } = MonoidAll;
    expect(concat(empty, true)).toBeTruthy;
    expect(concat(true, empty)).toBeTruthy;
    expect(concat(concat(true, false), false)).toBeTruthy;
    expect(concat(true, concat(false, false))).toBeTruthy;
  });

  test("5", function () {
    expect(concatAll(MonoidAny)([true, true, true])).toBeTruthy;
    expect(concatAll(MonoidAny)([false, false, false])).toBeFalsy;
    expect(concatAll(MonoidAny)([true, false, true])).toBeTruthy;
  });

  test("6", function () {
    expect(concatAll(MonoidAny)([true, true, true])).toBeTruthy;
    expect(concatAll(MonoidAny)([false, false, false])).toBeFalsy;
    expect(concatAll(MonoidAny)([true, false, true])).toBeTruthy;
  });

  test("8", function () {
    const { empty, concat } = arrayMonoid;
    expect(concat([true], empty)).toMatchObject([true]);
    expect(concat(empty, [true])).toMatchObject([true]);

    expect(concat([1], concat([2], [3]))).toMatchObject(
      concat(concat([1], [2]), [3])
    );
  });

  test("9", function () {
    const { empty, concat } = arrayMonoid;
    expect(concat([1, 2, [3, 4, 5]], empty)).toMatchObject([1, 2, [3, 4, 5]]);
    expect(concat(empty, [1, 2, [3, 4, 5]])).toMatchObject([1, 2, [3, 4, 5]]);
    expect(concat([1], concat([1, 2, [3, 4, 5]], [3]))).toMatchObject(
      concat(concat([1], [1, 2, [3, 4, 5]]), [3])
    );
  });

  test("10", function () {
    expect(
      concatAll(arrayMonoid)([
        [1, 2, 3],
        [4, 5],
        [5, 4, 5, 2],
      ])
    ).toMatchObject([1, 2, 3, 4, 5, 5, 4, 5, 2]);
    debugger;
    expect(
      foldMap(arrayMonoid)((arr: number[]) => arr.map((item) => item + 1))([
        [1, 2, 3],
        [4, 5],
        [5, 4, 5, 2],
      ])
    ).toMatchObject([2, 3, 4, 5, 6, 6, 5, 6, 3]);
  });

  test("11", function () {
    // 이해를 못했다.
  });

  test("12", function () {
    const contains = (target: number) =>
      foldMap(arrayMonoid)((arr: number[]) => [
        arr.some((item) => item === target),
      ]);
    expect(
      contains(4)([
        [1, 2, 3],
        [4, 5],
        [5, 4, 5, 2],
      ])
    ).toMatchObject([false, true, true]);
  });

  test("13", function () {
    // const contains = (value: number, tree: Tree<number>) =>
    //   FoldMap2(tree, (it: number) => it === value, MonoidAny);

    // const tree = new Node(
    //   true,
    //   new Node(true, new Empty(), new Empty()),
    //   new Empty()
    // );

    // expect(contains(true, tree));
  });

  test("maybeMonoidTest", function () {
    const result = maybeMonoid(MonoidSum).concat(some(1), some(2));
    expect(result).toMatchObject(some(3));
  });

  test("maybeMonoidTest2", function () {
    const result = maybeMonoid(MonoidSum).concat(none, some(2));
    expect(result).toMatchObject(some(2));
  });

  test("maybeMonoidTest3", function () {
    const result = maybeMonoid(MonoidSum).concat(some(2), none);
    expect(result).toMatchObject(some(2));
  });
});
