/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package timeline.graph;

/**
 *
 * @author ahassan
 */
public enum EventType {
    
    BIRTH("birth");
    
    private final String value;
    
    EventType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
