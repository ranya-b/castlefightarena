package com.ranyar.castlefightgraphic1.controllers;

import com.ranyar.castlefightgraphic1.App;
import com.ranyar.castlefightgraphic1.Databasemanager;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import com.ranyar.castlefightgraphic1.model.Personnage;
import com.ranyar.castlefightgraphic1.model.Guerrier;
import com.ranyar.castlefightgraphic1.model.Elfe;
import com.ranyar.castlefightgraphic1.model.Sorciere;
import com.ranyar.castlefightgraphic1.model.Nain;

public class SecondaryController {
    
    @FXML
    private ImageView Knight;
    
    @FXML
    private ImageView Elf;
    
    @FXML
    private ImageView Witch;
    
    @FXML
    private ImageView Dwarf;

    @FXML
    private ImageView perso1;

    @FXML
    private ImageView perso2;

    @FXML
    private Button quit;
    
    @FXML
    private Button back;

    @FXML
    private Button fight;

    @FXML
    private TextArea logCombat;

    private boolean slotToggle = false; // false = perso1, true = perso2
    private Timeline timelineCombat;

    @FXML
    private Button btnHistorique;

    @FXML
    public void initialize() {
        Knight.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCharacterClick(Knight));
        Elf.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCharacterClick(Elf));
        Witch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCharacterClick(Witch));
        Dwarf.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCharacterClick(Dwarf));
        
        quit.setOnAction(e -> handleQuit());
        back.setOnAction(e -> handleBack(e));
        fight.setOnAction(e -> handleFight());
        
        logCombat.setEditable(false);
        logCombat.setWrapText(true);

        btnHistorique.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/ranyar/castlefightgraphic1/history.fxml"));
                Stage stage = (Stage) btnHistorique.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void handleCharacterClick(ImageView imgView) {
        Image img = imgView.getImage();
        String choixNom = "";
        if (imgView == Knight) choixNom = "Knight";
        else if (imgView == Elf) choixNom = "Elf";
        else if (imgView == Witch) choixNom = "Witch";
        else if (imgView == Dwarf) choixNom = "Dwarf";
        if (!slotToggle) {
            perso1.setImage(img);
        } else {
            perso2.setImage(img);
        }
        slotToggle = !slotToggle;
    }

    private void handleQuit() {
        // Ferme la fenêtre courante
        quit.getScene().getWindow().hide();
    }

    private void handleBack(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/ranyar/castlefightgraphic1/primary.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void ouvrirHistorique(ActionEvent e) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/com/ranyar/castlefightgraphic1/history.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
    }

    @FXML
    private void handleFight() {
        // Vérifier que les deux personnages sont sélectionnés
        if (perso1.getImage() == null || perso2.getImage() == null) {
            logCombat.appendText("Veuillez selectionner deux personnages !\n");
            return;
        }

        // Créer les instances de personnages
        Personnage p1 = creerPersonnage(perso1.getImage());
        Personnage p2 = creerPersonnage(perso2.getImage());

        logCombat.clear();

        // Rediriger System.out pour capturer le déroulé du combat
        java.io.PrintStream oldOut = System.out;
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        System.setOut(ps);

        // Lancer le combat (utilise la méthode combattre de Personnage)
        p1.combattre(p2);
        // Après p1.combattre(p2) :
        String nomGagnant, nomPerdant;
        int vieRestante;

        if (p1.getVie() > 0) {
            nomGagnant = p1.getNom();
            nomPerdant = p2.getNom();
            vieRestante = p1.getVie();
        } else {
            nomGagnant = p2.getNom();
            nomPerdant = p1.getNom();
            vieRestante = p2.getVie();
        }

        Databasemanager.sauvegarderCombat(nomGagnant, nomPerdant, vieRestante);

        // Récupérer le texte capturé
        System.out.flush();
        System.setOut(oldOut);
        String texteCombat = baos.toString();

                // Découper le texte en lignes
                String[] lignes = texteCombat.split("\n");
        
                // Créer une Timeline pour afficher chaque ligne progressivement
                final int[] index = {0};
                timelineCombat = new Timeline();
                
                KeyFrame keyFrame = new KeyFrame(Duration.millis(700), event -> {
                    if (index[0] < lignes.length) {
                        logCombat.appendText(lignes[index[0]] + "\n");
                        logCombat.setScrollTop(Double.MAX_VALUE);
                        index[0]++;
                    } else {
                        timelineCombat.stop();
                    }
                });
                
                timelineCombat.getKeyFrames().add(keyFrame);
                timelineCombat.setCycleCount(lignes.length);
                timelineCombat.play();
    }

    private Personnage creerPersonnage(Image img) {
        if (img == null || img.getUrl() == null) {
            return null;
        }
        String url = img.getUrl();
        if (url.contains("knight")) {
            return new Guerrier("Guerrier");
        } else if (url.contains("elf")) {
            return new Elfe("Elfe");
        } else if (url.contains("witch")) {
            return new Sorciere("Sorcière");
        } else if (url.contains("dwarf")) {
            return new Nain("Nain");
        }
        return null;
    }
}