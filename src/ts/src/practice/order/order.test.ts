import { getOlder, getOlder2, getYounger, min, OrdNumber } from "./order";

describe("test min func", function () {
  test("test", function () {
    expect(min(OrdNumber)(3, 5)).toBe(3);
    expect(min(OrdNumber)(3, 5)).toBe(min(OrdNumber)(3, 5));
    expect(min(OrdNumber)(5, 3)).toBe(min(OrdNumber)(3, 5));
  });
});

describe("byAge 함수와 min 함수를 사용하는 getYounger 함수 테스트", () => {
  it("getYounger 함수 테스트 ", () => {
    expect(
      getYounger({ name: "hs", age: 29 }, { name: "sam", age: 32 })
    ).toMatchObject({ name: "hs", age: 29 });
  });
});

describe("getOlder test", () => {
  it("getOlder test", () => {
    expect(
      getOlder({ name: "hs", age: 29 }, { name: "sam", age: 32 })
    ).toMatchObject({ name: "sam", age: 32 });
  });
  it("getOlder2 test", () => {
    expect(
      getOlder2({ name: "hs", age: 29 }, { name: "sam", age: 32 })
    ).toMatchObject({ name: "sam", age: 32 });
  });
});
