import { monoidVector } from "./monoid";

describe("test monoid vector", function () {
  test("test monoid", function () {
    expect(
      monoidVector.concat(
        { from: { x: 0, y: 0 }, to: { x: 10, y: 10 } },
        { from: { x: 0, y: 0 }, to: { x: 10, y: 10 } }
      )
    ).toMatchObject({ from: { x: 0, y: 0 }, to: { x: 20, y: 20 } });
  });
});
