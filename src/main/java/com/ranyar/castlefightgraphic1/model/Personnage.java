/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranyar.castlefightgraphic1.model;

import java.util.Random;

/**
 *
 * @author Utilisateur
 */
public abstract class Personnage {

    //attributs
    String nom;
    int vie;

    //constructeur
    public Personnage(String nom) {
        this.nom = nom;
        this.vie = 100;
    }

    //getters et setters
    public String getNom() {
        return nom;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    //méthodes
    public void combattre(Personnage p) {
        System.out.println("Combat entre " + this.getNom());
        this.sePresenter();
        System.out.println("et "+p.getNom());
        p.sePresenter();
        System.out.println("C'est parti !");
        System.out.println("---------------------");

        while (this.getVie() > 0 && p.getVie() > 0) {
            p.setVie(p.getVie() - this.frapper());
            System.out.println(p.getNom() + " a " + p.getVie() + " de vie");
            if (p.getVie() < 1) {
                System.out.println(this.getNom() + " gagne !");
            } else {
                this.setVie(this.getVie() - p.frapper());
                System.out.println(this.getNom() + " a " + this.getVie() + " de vie");
                if (this.getVie() < 1) {
                    System.out.println(p.getNom() + " gagne !");
                }
            }
        }
    }

    protected int calculerDegats() {
        Random rand = new Random();
        return rand.nextInt(20);
    }

    public abstract int frapper();

    public abstract void sePresenter();
}
