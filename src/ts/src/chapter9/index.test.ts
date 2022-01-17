import { concatAll } from "fp-ts/lib/Monoid";
import { none, some } from "fp-ts/lib/Option";
import { maybeMonoid, Monoid } from ".";

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

  test("7", function () {
    expect(concatAll(MonoidAll)([true, true, true])).toBeTruthy;
    expect(concatAll(MonoidAll)([false, false, false])).toBeFalsy;
    expect(concatAll(MonoidAll)([true, false, true])).toBeTruthy;
  });

  test("maybeMonoidTest", function () {
    debugger;
    const result = maybeMonoid(MonoidSum).concat(some(1), some(2));
    expect(result).toMatchObject(some(3));
  });

  test("maybeMonoidTest2", function () {
    debugger;
    const result = maybeMonoid(MonoidSum).concat(none, some(2));
    expect(result).toMatchObject(some(2));
  });

  test("maybeMonoidTest3", function () {
    debugger;
    const result = maybeMonoid(MonoidSum).concat(some(2), none);
    expect(result).toMatchObject(some(2));
  });
});
