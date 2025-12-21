package model.placeableObject.Weapon;

import model.grid.Grid;

public class Bomb extends Weapon {

    public Bomb() {
        super(WeaponType.BOMB);
    }

    @Override
    public WeaponType getType() {
        return WeaponType.BOMB;
    }

    @Override
    public void use(Grid grid, int centerPos) {
        int size = grid.getSize();

        // 1. Convert 1D index to 2D coordinates (to handle neighbors easily)
        int cx = centerPos % size;
        int cy = centerPos / size;

        // 2. VALIDATION PHASE: Check the 3x3 area for LAND
        // We assume the bomb covers from (cx-1, cy-1) to (cx+1, cy+1)
        for (int y = cy - 1; y <= cy + 1; y++) {
            for (int x = cx - 1; x <= cx + 1; x++) {

                // Check bounds (handle corners/edges)
                if (isInsideGrid(x, y, size)) {
                    int pos = y * size + x; // Convert back to 1D index

                    // The Condition: If ANY tile in the blast zone is LAND, abort.
                    if (grid.isLand(pos)) {

                        // Even if failed, we must notify the controller that the action is over
                        notifyFinished();
                        return; // Stop execution immediately
                    }
                }
            }
        }

        // 3. EXECUTION PHASE: Apply damage
        // If we reach here, it means NO land was found. It is safe to explode.
        for (int y = cy - 1; y <= cy + 1; y++) {
            for (int x = cx - 1; x <= cx + 1; x++) {

                if (isInsideGrid(x, y, size)) {
                    int pos = y * size + x;

                    // Apply the hit on the grid
                    grid.hitTile(pos);
                }
            }
        }

        // 4. Signal the end of the weapon animation/logic to the Controller
        notifyFinished();
    }

    /**
     * Helper to check grid boundaries to avoid IndexOutOfBoundsException
     */
    private boolean isInsideGrid(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}