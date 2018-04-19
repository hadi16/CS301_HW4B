package edu.up.cs301.qwirkle.tile;

import java.io.Serializable;

/**
 * Enum: QwirkleColor
 * The possible colors in the Qwirkle game.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 18, 2018
 */
public enum QwirkleColor implements Serializable {
    blue, green, orange, purple, red, yellow;

    // For serialization (network play)
    private static final long serialVersionUID = 3824829472471329419L;
}
