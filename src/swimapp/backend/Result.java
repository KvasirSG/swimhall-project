package swimapp.backend;

/**
 * Represents the result of a swimmer in a competition.
 */
public class Result {
    private int swimmerID;
    private int competitionID;
    private int placement;
    private double time;

    /**
     * Constructs a Result with the specified swimmer ID, competition ID, placement, and time.
     *
     * @param swimmerID the ID of the swimmer
     * @param competitionID the ID of the competition
     * @param placement the placement of the swimmer in the competition
     * @param time the time achieved by the swimmer
     */
    public Result(int swimmerID, int competitionID, int placement, double time) {
        this.swimmerID = swimmerID;
        this.competitionID = competitionID;
        this.placement = placement;
        this.time = time;
    }

    /**
     * Gets the ID of the swimmer.
     *
     * @return the ID of the swimmer
     */
    public int getSwimmerID() {
        return swimmerID;
    }

    /**
     * Gets the ID of the competition.
     *
     * @return the ID of the competition
     */
    public int getCompetitionID() {
        return competitionID;
    }

    /**
     * Gets the placement of the swimmer in the competition.
     *
     * @return the placement of the swimmer
     */
    public int getPlacement() {
        return placement;
    }

    /**
     * Sets the placement of the swimmer in the competition.
     *
     * @param placement the new placement of the swimmer
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }

    /**
     * Gets the time achieved by the swimmer.
     *
     * @return the time achieved by the swimmer
     */
    public double getTime() {
        return time;
    }
}
