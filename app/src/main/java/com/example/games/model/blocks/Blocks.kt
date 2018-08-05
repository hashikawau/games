package com.example.games.model.blocks

import com.example.games.model.Field

fun makeSingleBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(arrayOf(
                    Position(0, 0))),
            field,
            Field.Space.RECTANGLE,
            x,
            y)
}

fun makeRectangleBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(arrayOf(
                    Position(0, 0),
                    Position(0, 1),
                    Position(1, 0),
                    Position(1, 1))),
            field,
            Field.Space.RECTANGLE,
            x,
            y)
}

fun makeStraightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(0, 0),
                            Position(0, 1),
                            Position(0, 2),
                            Position(0, 3)),
                    arrayOf(Position(-1, 1),
                            Position(0, 1),
                            Position(1, 1),
                            Position(2, 1))),
            field,
            Field.Space.STRAIGHT,
            x,
            y)
}

fun makeGapLeftBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(0, 0),
                            Position(1, 0),
                            Position(1, 1),
                            Position(2, 1)),
                    arrayOf(Position(0, 1),
                            Position(0, 2),
                            Position(1, 0),
                            Position(1, 1))),
            field,
            Field.Space.GAP_LEFT,
            x,
            y)
}

fun makeGapRightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(0, 1),
                            Position(1, 0),
                            Position(1, 1),
                            Position(2, 0)),
                    arrayOf(Position(0, 0),
                            Position(0, 1),
                            Position(1, 1),
                            Position(1, 2))),
            field,
            Field.Space.GAP_RIGHT,
            x,
            y)
}

fun makeHookLeftBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(-1, 0),
                            Position(-1, 1),
                            Position(0, 1),
                            Position(1, 1)),
                    arrayOf(Position(0, 0),
                            Position(0, 1),
                            Position(0, 2),
                            Position(1, 0)),
                    arrayOf(Position(-1, 0),
                            Position(0, 0),
                            Position(1, 0),
                            Position(1, 1)),
                    arrayOf(Position(0, 2),
                            Position(1, 0),
                            Position(1, 1),
                            Position(1, 2))),
            field,
            Field.Space.HOOK_LEFT,
            x,
            y)
}

fun makeHookRightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(-1, 1),
                            Position(0, 1),
                            Position(1, 0),
                            Position(1, 1)),
                    arrayOf(Position(0, 0),
                            Position(0, 1),
                            Position(0, 2),
                            Position(1, 2)),
                    arrayOf(Position(-1, 0),
                            Position(-1, 1),
                            Position(0, 0),
                            Position(1, 0)),
                    arrayOf(Position(0, 0),
                            Position(1, 0),
                            Position(1, 1),
                            Position(1, 2))),
            field,
            Field.Space.HOOK_RIGHT,
            x,
            y)
}

fun makeHookCenterBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(-1, 1),
                            Position(0, 0),
                            Position(0, 1),
                            Position(1, 1)),
                    arrayOf(Position(0, 0),
                            Position(0, 1),
                            Position(0, 2),
                            Position(1, 1)),
                    arrayOf(Position(-1, 1),
                            Position(0, 1),
                            Position(0, 2),
                            Position(1, 1)),
                    arrayOf(Position(-1, 1),
                            Position(0, 0),
                            Position(0, 1),
                            Position(0, 2))),
            field,
            Field.Space.HOOK_CENTER,
            x,
            y)
}
