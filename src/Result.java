public class Result {
    private int swimmerID;
    private int competitionID;
    private int placement;
    private double time;

    public Result(int swimmerID, int competitionID, int placement, double time) {
        this.swimmerID = swimmerID;
        this.competitionID = competitionID;
        this.placement = placement;
        this.time = time;
    }

    public int getSwimmerID() {
        return swimmerID;
    }

    public int getCompetitionID() {
        return competitionID;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public double getTime() {
        return time;
    }
}
