import { elem, eqNumber } from "./equality";

describe("elem 함수 테스트", () => {
  it("eqNumber를 이용한 elem 함수 테스트 (요소가 있는 경우)", () => {
    expect(elem(eqNumber)(1, [1, 2, 3])).toBeTruthy();
  });
  it("eqNumber를 이용한 elem 함수 테스트 (요소가 없는 경우)", () => {
    expect(elem(eqNumber)(4, [1, 2, 3])).toBeFalsy();
  });
});
