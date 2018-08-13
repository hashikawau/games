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

/**
 * ....
 * .oo.
 * .oo.
 * ....
 */
fun makeRectangleBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(arrayOf(
                    Position(1, 1),
                    Position(1, 2),
                    Position(2, 1),
                    Position(2, 2))),
            field,
            Field.Space.RECTANGLE,
            x,
            y)
}

/**
 * .o..    ....
 * .o.. -> oooo
 * .o..    ....
 * .o..    ....
 */
fun makeStraightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(1, 2),
                            Position(1, 3)),
                    arrayOf(Position(0, 1),
                            Position(1, 1),
                            Position(2, 1),
                            Position(3, 1))),
            field,
            Field.Space.STRAIGHT,
            x,
            y)
}

/**
 * ..o.    .oo.
 * .oo. -> ..oo
 * .o..    ....
 * ....    ....
 */
fun makeGapLeftBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(1, 1),
                            Position(1, 2),
                            Position(2, 0),
                            Position(2, 1)),
                    arrayOf(Position(1, 0),
                            Position(2, 0),
                            Position(2, 1),
                            Position(3, 1))),
            field,
            Field.Space.GAP_LEFT,
            x,
            y)
}

/**
 * .o..    .oo.
 * .oo. -> oo..
 * ..o.    ....
 * ....    ....
 */
fun makeGapRightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(2, 1),
                            Position(2, 2)),
                    arrayOf(Position(0, 1),
                            Position(1, 0),
                            Position(1, 1),
                            Position(2, 0))),
            field,
            Field.Space.GAP_RIGHT,
            x,
            y)
}

/**
 * .oo.    ooo.    ..o.    .o..
 * .o.. -> ..o. -> ..o. -> .ooo
 * .o..    ....    .oo.    ....
 * ....    ....    ....    ....
 */
fun makeHookLeftBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(1, 2),
                            Position(2, 0)),
                    arrayOf(Position(0, 0),
                            Position(1, 0),
                            Position(2, 0),
                            Position(2, 1)),
                    arrayOf(Position(1, 2),
                            Position(2, 0),
                            Position(2, 1),
                            Position(2, 2)),
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(2, 1),
                            Position(3, 1))),
            field,
            Field.Space.HOOK_LEFT,
            x,
            y)
}

/**
 * .oo.    ..o.    .o..    .ooo
 * ..o. -> ooo. -> .o.. -> .o..
 * ..o.    ....    .oo.    ....
 * ....    ....    ....    ....
 */
fun makeHookRightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(1, 0),
                            Position(2, 0),
                            Position(2, 1),
                            Position(2, 2)),
                    arrayOf(Position(0, 1),
                            Position(1, 1),
                            Position(2, 0),
                            Position(2, 1)),
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(1, 2),
                            Position(2, 2)),
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(2, 0),
                            Position(3, 0))),
            field,
            Field.Space.HOOK_RIGHT,
            x,
            y)
}

/**
 * .o..    .o..    ooo.    .o..
 * ooo. -> .oo. -> .o.. -> oo..
 * ....    .o..    ....    .o..
 * ....    ....    ....    ....
 */
fun makeHookCenterBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
): CompositeBlock {
    return CompositeBlock(
            arrayOf(
                    arrayOf(Position(0, 1),
                            Position(1, 0),
                            Position(1, 1),
                            Position(2, 1)),
                    arrayOf(Position(1, 0),
                            Position(1, 1),
                            Position(1, 2),
                            Position(2, 1)),
                    arrayOf(Position(0, 1),
                            Position(1, 1),
                            Position(1, 2),
                            Position(2, 1)),
                    arrayOf(Position(0, 1),
                            Position(1, 0),
                            Position(1, 1),
                            Position(1, 2))),
            field,
            Field.Space.HOOK_CENTER,
            x,
            y)
}
