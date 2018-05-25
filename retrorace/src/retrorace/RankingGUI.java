/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import entities.Ranking;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class RankingGUI extends JPanel {

    private DBManager dbManager;
    private String username;

    private JLabel[][] lblRanking;

    public RankingGUI(DBManager dbm, String user) {
        dbManager = dbm;
        username = user;

        createElements();
    }

    public void getGlobalRankingInMap(int idMap) {
        List<Ranking> r = dbManager.getGlobalRankingInMap(idMap);
        putDataInRanking(r);
        
    }

    public void getPersonalRankingInMap(int idMap) {
        List<Ranking> r = dbManager.getPersonalRankingInMap(idMap, username);
        putDataInRanking(r);
    }

    public void saveRecord(int idMap, int time) {
        Ranking r = new Ranking();
        r.setIdMap(idMap);
        r.setTime(time);
        r.setUsername(username);

        dbManager.saveRecord(r);
    }

    private void createElements() {
        this.setBorder(BorderFactory.createEmptyBorder(75, 0, 75, 0));
        this.setLayout(new GridLayout(11, 2, 5, 5));
        lblRanking = new JLabel[10][2];

        JLabel lblUsername = new JLabel("Usuario");
        lblUsername.setFont(new Font("Arial Black", 0, 28));
        lblUsername.setForeground(Color.ORANGE);
        lblUsername.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblTime = new JLabel("Tiempo");
        lblTime.setFont(new Font("Arial Black", 0, 28));
        lblTime.setForeground(Color.ORANGE);
        lblTime.setHorizontalAlignment(JLabel.CENTER);

        this.add(lblUsername);
        this.add(lblTime);

        for (int i = 0; i < lblRanking.length; i++) {
            lblRanking[i][0] = new JLabel("-");
            lblRanking[i][0].setFont(new Font("Arial Black", 0, 20));
            lblRanking[i][0].setForeground(Color.BLUE);
            lblRanking[i][0].setHorizontalAlignment(JLabel.CENTER);
            this.add(lblRanking[i][0]);

            lblRanking[i][1] = new JLabel("-:--");
            lblRanking[i][1].setFont(new Font("Arial Black", 0, 20));
            lblRanking[i][1].setForeground(Color.BLUE);
            lblRanking[i][1].setHorizontalAlignment(JLabel.CENTER);
            this.add(lblRanking[i][1]);
        }
    }

    private String parseTime(Integer time) {
        int min = time/60;
        int seg = time%60;
        
        if(seg<10){
            return min+":0"+seg;
        }else{
            return min+":"+seg;
        }    
        
    }

    private void putDataInRanking(List<Ranking> r) {
        for (int i = 0; i < lblRanking.length; i++) {
            if(i<r.size()){
                lblRanking[i][0].setText(r.get(i).getUsername());
                lblRanking[i][1].setText(parseTime(r.get(i).getTime()));
            }else{
                lblRanking[i][0].setText("-");
                lblRanking[i][1].setText("-:--");
            }
        }
    }

}
