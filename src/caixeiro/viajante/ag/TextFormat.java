/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixeiro.viajante.ag;

/**
 *
 * @author dalbosco
 */
public class TextFormat {

    private String text = "";

    public TextFormat() {
        this.text = "";
    }

    public String getText() {
        return text;
    }

    public void concatenateText(String text) {
        this.text += text;
    }
}
