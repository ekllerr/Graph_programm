package org.example;

public class GraphNode {
    private int id;
    private String label;
    private int eccentricity;

    public GraphNode(int id){
        this.setId(id);
        this.setLabel("");
        this.setEccentricity(-1);
    }

    public GraphNode(int id, int eccentricity){
        this.setId(id);
        this.setLabel("");
        this.setEccentricity(eccentricity);
    }

    public GraphNode(int id, String label, int eccentricity){
        this.setId(id);
        this.setLabel(label);
        this.setEccentricity(eccentricity);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(int eccentricity) {
        this.eccentricity = eccentricity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Knote: ").append(id);
        if(!this.label.isBlank()) sb.append(", ").append(label);
        if(this.eccentricity == -1) sb.append(", Exzentrizitäten noch nicht berechnet");
        else sb.append(", Exzentrizitäten: ").append(eccentricity);
        return sb.toString();
    }
}
