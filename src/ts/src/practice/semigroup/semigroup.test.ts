import { isNone, isSome, none, some } from "fp-ts/lib/Option";
import { isPositiveXY, product, S, sum } from "./semigroup";

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
describe('Option타입을 지원하는 appliedSemigroup 인스턴스 테스트', () => {
    let result;
    it('appliedSemigroup 테스트 (some + none)', () => {
      result = S.concat(some(1), none);
      expect(result).toBe(none);
      expect(isNone(result)).toBeTruthy();
    });
    it('appliedSemigroup 테스트 (some + some)', () => {
      result = S.concat(some(1), some(2));
      expect(result).toMatchObject(some(3));
      expect(isSome(result)).toBeTruthy();
    });
  });