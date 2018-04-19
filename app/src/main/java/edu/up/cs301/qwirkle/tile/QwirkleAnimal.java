package edu.up.cs301.qwirkle.tile;

import java.io.Serializable;

/**
 * Enum: QwirkleAnimal
 * The possible animals in the Qwirkle game.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 18, 2018
 */
public enum QwirkleAnimal implements Serializable {
    bat, dog, fox, owl, parrot, snake;

    // For serialization (network play)
    private static final long serialVersionUID = 4718419248410841248L;
}

