import { isNone, isSome, none, some } from "fp-ts/lib/Option";
import { isPositiveXY, product, S, semigroupCustomer, sum } from "./semigroup";

describe("semigroupPredicate 인스턴스를 이용해 만든 isPositiveXY 테스트", () => {
  it("isPositiveXY 함수 테스트", () => {
    expect(isPositiveXY({ x: 1, y: 1 })).toBeTruthy();
    expect(isPositiveXY({ x: 1, y: -1 })).toBeFalsy();
    expect(isPositiveXY({ x: -1, y: 1 })).toBeFalsy();
    expect(isPositiveXY({ x: -1, y: -1 })).toBeFalsy();
  });
});

describe("concatAll, SemigroupSum를 사용한 sum 함수 테스트", () => {
  it("sum함수 테스트", () => {
    expect(sum(0)([1, 2, 3, 4])).toBe(10);
  });
});

describe("product", () => {
  it("product함수 테스트", () => {
    expect(product(0)([1, 2, 3, 4])).toBe(0);
    expect(product(1)([2, 2, 2, 2])).toBe(16);
  });
});

// ? functor와 비슷한듯 하다..?
describe("Option타입을 지원하는 appliedSemigroup 인스턴스 테스트", () => {
  let result;
  it("appliedSemigroup 테스트 (some + none)", () => {
    result = S.concat(some(1), none);
    expect(result).toBe(none);
    expect(isNone(result)).toBeTruthy();
  });
  it("appliedSemigroup 테스트 (some + some)", () => {
    result = S.concat(some(1), some(2));
    expect(result).toMatchObject(some(3));
    expect(isSome(result)).toBeTruthy();
  });
});

describe("Semigroup 인터페이스를 구현한 semigroupCustomer 인스턴스 테스트", () => {
  it("semigroupCustomer 인스턴스 concat 함수 테스트", () => {
    expect(
      semigroupCustomer.concat(
        {
          name: "Giulio",
          favouriteThings: ["math", "climbing"],
          registeredAt: new Date(2018, 1, 20).getTime(),
          lastUpdatedAt: new Date(2018, 2, 18).getTime(),
          hasMadePurchase: false,
        },
        {
          name: "Giulio Canti",
          favouriteThings: ["functional programming"],
          registeredAt: new Date(2018, 1, 22).getTime(),
          lastUpdatedAt: new Date(2018, 2, 9).getTime(),
          hasMadePurchase: true,
        }
      )
    ).toMatchObject({
      name: "Giulio Canti",
      favouriteThings: ["math", "climbing", "functional programming"],
      registeredAt: new Date(2018, 1, 20).getTime(),
      lastUpdatedAt: new Date(2018, 2, 18).getTime(),
      hasMadePurchase: true,
    });
  });
});
