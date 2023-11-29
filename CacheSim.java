import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CacheSim {

    private final HashMap<Integer, ArrayList<Integer>> cacheMem = new HashMap<>();
    private final int setSize = 8; //number of blocks per set
    private final int cacheBlocks = 32; //total number of blocks in cache
    private final int numSets = cacheBlocks / setSize;
    private int numHits;
    private int numMiss;
    // set bits = 2;

    public void initCache(){
        numHits = 0;
        numMiss = 0;
        // 4 sets

        for(int i = 0; i< numSets; i++){
            ArrayList<Integer> cacheBlock = new ArrayList<>();
            cacheMem.put(i, cacheBlock);
        }
    }

    public void resetSim(){
        numHits = 0;
        numMiss = 0;
        for(int i = 0; i< numSets; i++){
            cacheMem.get(i).clear();
        }
    }


    public int[] initSeqTest(){
        int[] memAddresses = new int[cacheBlocks*2];

        for(int i = 0; i<cacheBlocks*2;i++){
            memAddresses[i] = i;
        }
        return memAddresses;
    }

    public int[] initRandTest(){
        Random r = new Random();
        int[] memAddresses = new int[cacheBlocks*4];
        for(int i = 0; i<cacheBlocks*4;i++){
            memAddresses[i] = r.nextInt(128);
        }
        return memAddresses;
    }


    public int[] initMidRepBlockTest(){
        int memSpace = cacheBlocks*2+cacheBlocks-2;
        int[] memAddresses = new int[memSpace*4];
        int index = 0;
        for(int i = 0; i<4; i++){

            for (int j = 0; j<cacheBlocks-1;j++){
                memAddresses[index] = j;
                index++;
            }
            for (int j = 1; j<cacheBlocks-1; j++){
                memAddresses[index] = j;
                index++;
            }
            for (int j = cacheBlocks-1; j < cacheBlocks*2; j++){
                memAddresses[index] = j;
                index++;
            }
        }



        return memAddresses;
    }
    

    //TODO finish the func
    public float getAvgAccessTime(){
        int totalAccess = numHits + numMiss;
        float cacheHitRate = (float) numHits/totalAccess;
        float cacheMissRate = (float) numMiss/totalAccess;
        int missPenalty = 11;
        return cacheHitRate * 1 + cacheMissRate * missPenalty;
    }

    //TODO finish the func
    public double getTotalAccessTime(){

        //number of words
        int cacheLine = 64;
        return ((numHits*cacheBlocks* cacheLine *1.0) + (numMiss*cacheBlocks* cacheLine *10.0) + (float) numMiss);
    }

    public int getNumHits() {
        return numHits;
    }

    public void setNumHits(int numHits) {
       this.numHits += numHits;
    }

    public int getNumMiss() {
        return numMiss;
    }

    public void setNumMiss(int numMiss) {
        this.numMiss += numMiss;
    }

    public HashMap<Integer, ArrayList<Integer>> getCacheMem() {
        return cacheMem;
    }

    public int getSetSize() {
        return setSize;
    }


}

class CacheGUI extends JFrame{

    JPanel cachePanel = new JPanel();
    JPanel memPanel = new JPanel();
    JPanel btnPanel = new JPanel();
    JButton seqBtn = new JButton("Sequential Sequence");
    JButton randBtn = new JButton("Random Sequence");
    JButton repeatBtn = new JButton("Mid-repeat Blocks");
    JToggleButton trackBtn = new JToggleButton("Show Tracing");

    JTextArea infoText = new JTextArea(5,30);
    JButton startBtn = new JButton("Start Simulation");
    JButton resetBtn = new JButton("Reset Simulation");

    JTextArea memText = new JTextArea(5,30);
    JTextArea endText = new JTextArea(0,30);
    JTextArea customAdd = new JTextArea(5, 30);

    ArrayList<JLabel> cacheContents = new ArrayList<>();

    public CacheGUI(){
        super("Cache Simulation Project");
        this.setLayout(new BorderLayout());

        addCachePanel();
        addMemPanel();
        addButtonsPanel();

        this.setSize(1500, 1000);
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addCachePanel(){
        cachePanel.setLayout(new BorderLayout());


        JPanel cacheGrid = new JPanel();
        cacheGrid.setLayout(new GridLayout(0, 9));
        cacheGrid.setBackground(Color.decode("#c4c8fb"));
        cacheGrid.setPreferredSize(new Dimension(1000,800));

        //adding header labels
        String text;
        for(int i = 0; i < 45; i++){
            JLabel label = new JLabel("");
            if (i <1){
                text = "";
            }else if (i<9){
                text = "Block " + (i-1);
            }else if (i%9 == 0){
                text = "Set " + ((i-9)/9);
            }else{
                text = "";
                cacheContents.add(label);
                label.setBorder(new BevelBorder(BevelBorder.RAISED,Color.decode("#60617a"), Color.decode("#60617a")));
            }
            label.setText(text);
            label.setHorizontalAlignment(JLabel.CENTER);
            cacheGrid.add(label);

        }
        cacheGrid.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.decode("#23242d"), Color.decode("#23242d")));


        cachePanel.add(cacheGrid, BorderLayout.CENTER);
        this.add(cachePanel,BorderLayout.WEST);

    }

    public void addMemPanel(){
        memPanel.setLayout(new BorderLayout());
        memPanel.setPreferredSize(new Dimension(500, 800));
        memPanel.setBackground(Color.decode("#b5bec1"));
        memPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        //bottom of mempanel will have the hit/miss rate, other computations
        JScrollPane memScroll = new JScrollPane(memText);
        memScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        memText.setEditable(false);

        memPanel.add(memScroll, BorderLayout.CENTER);

        endText.setPreferredSize(new Dimension(500, 130));
        endText.setEditable(false);
        memPanel.add(endText, BorderLayout.SOUTH);

        this.add(memPanel, BorderLayout.EAST);
    }

    public void addButtonsPanel(){
        btnPanel.setLayout(new BorderLayout());
        btnPanel.setPreferredSize(new Dimension(1500, 200));

        addTestTypesPanel();
        addSelectionInfoPanel();
        addSimControlPanel();

        this.add(btnPanel, BorderLayout.SOUTH);
    }

    public void addTestTypesPanel(){
        JLabel testLabel = new JLabel("Select test type below: ");
        JPanel testTypePanel = new JPanel();
        testTypePanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.gridy = 0;
        c.ipady = 10;
        testLabel.setHorizontalAlignment(JLabel.CENTER);
        testTypePanel.add(testLabel,c);

        seqBtn.setActionCommand("seq");
        c.gridwidth = 1;
        c.ipady = 40;
        c.gridy = 1;
        c.gridx = 0;
        testTypePanel.add(seqBtn,c);

        randBtn.setActionCommand("rand");
        c.gridx = 1;
        testTypePanel.add(randBtn,c);

        repeatBtn.setActionCommand("repeat");
        c.gridx = 2;
        testTypePanel.add(repeatBtn,c);

        trackBtn.setActionCommand("track");
        c.gridy = 2;
        c.gridwidth = 3;
        c.gridx = 0;
        testTypePanel.add(trackBtn,c);


        btnPanel.add(testTypePanel, BorderLayout.WEST);
    }
    public void addSelectionInfoPanel(){
        JPanel selectInfoPanel = new JPanel();
        selectInfoPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        infoText.setEditable(false);
        String text =
                """
                Selected Simulation Test Case: None
                Show Tracing: False
                Simulation Status: Not Started
                For custom memory block input:
                Separate addresses with a comma (,)
                """;
        infoText.setText(text);

        infoText.setBackground(Color.decode("#bdbfc6"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        selectInfoPanel.add(infoText, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        c.weighty = 0;

        customAdd.setLineWrap(true);
        c.gridy = 2;
        c.gridheight = 1;
        selectInfoPanel.add(customAdd, c);

        btnPanel.add(selectInfoPanel, BorderLayout.CENTER);



    }
    public void addSimControlPanel(){
        JPanel simControl = new JPanel();
        simControl.setLayout(new GridBagLayout());
        simControl.setPreferredSize(new Dimension(700,200));

        GridBagConstraints c = new GridBagConstraints();

        startBtn.setActionCommand("start");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.weighty = .5;
        c.ipady = 60;
        c.weightx = .5;
        simControl.add(startBtn, c);
        resetBtn.setActionCommand("reset");
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.weightx = 0;
        simControl.add(resetBtn, c);



        btnPanel.add(simControl, BorderLayout.EAST);


    }

    public ArrayList<JLabel> getCacheContents(){
        return this.cacheContents;
    }

    public void setActionCommandListener(ActionListener a){
        seqBtn.addActionListener(a);
        randBtn.addActionListener(a);
        repeatBtn.addActionListener(a);
        startBtn.addActionListener(a);
        resetBtn.addActionListener(a);

    }

    public void setItemListener(ItemListener i){
        trackBtn.addItemListener(i);
    }

    public void setDocumentListener(DocumentListener l){
        customAdd.getDocument().addDocumentListener(l);
    }



    public JToggleButton getTrackBtn() {
        return trackBtn;
    }

    public JTextArea getInfoText() {
        return infoText;
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JTextArea getMemText() {
        return memText;
    }

    public JButton getSeqBtn() {
        return seqBtn;
    }

    public JButton getRandBtn() {
        return randBtn;
    }

    public JButton getRepeatBtn() {
        return repeatBtn;
    }

    public JTextArea getEndText() {
        return endText;
    }

    public JTextArea getCustomAdd() {
        return customAdd;
    }

}

class CacheController implements ActionListener, ItemListener, DocumentListener{

    String a;
    String customAdd;
    boolean custom = false;
    String[] addresses;

    CacheSim sim;
    CacheGUI gui;
    String simType = "seq";
    boolean typeSelected = false;
    boolean showTrace = false;
    String status = "Not Started";
    String memStatus;
    String testType;

    CacheController(CacheSim sim, CacheGUI gui){
        this.sim = sim;
        this.gui = gui;

        sim.initCache();
        this.gui.setActionCommandListener(this);
        this.gui.setItemListener(this);
        this.gui.setDocumentListener(this);
        updateInfoText("");
    }

    public void updateInfoText(String action){
         testType = switch (simType){
            case "rand" -> "Random Sequence";
            case "repeat" -> "Mid-repeat Blocks";
            case "seq" -> "Sequential Sequence";
            default -> "Custom";
        };
        if (!typeSelected && !custom){
            testType = "None";
        }
        if (action.equals("Custom"))
            testType = "Custom";
        String text =
                "Selected Simulation Test Case: " + testType +
                        "\nShow Tracing: " + showTrace +
                        "\nSimulation Status: " + status +
                        "\n\nFor custom memory block input: \nSeparate addresses with a comma (,) only";
        gui.getInfoText().setText(text);
        if (action.equals("reset")){
            gui.getCustomAdd().setText("");
            gui.getSeqBtn().setEnabled(true);
            gui.getRandBtn().setEnabled(true);
            gui.getRepeatBtn().setEnabled(true);
            custom = false;
            customAdd = "";
            addresses = null;
            typeSelected = false;
            gui.getCustomAdd().setEnabled(true);
        }


        gui.getStartBtn().setEnabled(typeSelected || custom);
    }

    public void startSim() throws InterruptedException {
        gui.getCustomAdd().setEnabled(false);
        int[] memAddresses;
        if(custom)
            simType = "custom";
        switch (simType) {
            case "rand" -> memAddresses = sim.initRandTest();
            case "repeat" -> memAddresses = sim.initMidRepBlockTest();
            case "seq" -> memAddresses = sim.initSeqTest();
            default -> {
                memAddresses = new int[addresses.length];
                for(int i = 0; i<addresses.length;i++){
                    try {
                        memAddresses[i]=Integer.parseInt(addresses[i]);
                    }catch (Exception e){
                        status = "error";
                    }
                }
            }
        }

        final int[] ctr = {0};
            SwingWorker<Void, String> w = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for(int i: memAddresses){
                        if(showTrace)
                            Thread.sleep(500);
                        int numBlocks = sim.getSetSize();
                        int blockNumber = 0;
                        JLabel replace= new JLabel();

                        String isHit;
                        int setNum = i % 4;
                        ArrayList<Integer> currSet = sim.getCacheMem().get(setNum);

                        if(currSet.isEmpty()){
                            currSet.add(i);
                            sim.setNumMiss(1);
                            isHit = "Miss";
                            replace = gui.getCacheContents().get(setNum*8);
                        }else{
                            if (currSet.contains(i)){
                                sim.setNumHits(1);
                                isHit= "Hit";
                            }else{
                                isHit = "Miss";
                                sim.setNumMiss(1);
                                if(currSet.size() == numBlocks) {
                                    int compare = currSet.get(0);
                                    for(JLabel temp: gui.getCacheContents()){
                                        String address = temp.getText();
                                        if(String.valueOf(compare).equals(address)){
                                            replace = temp;
                                            break;
                                        }
                                    }
                                    currSet.remove(0);
                                }else{
                                    replace = gui.getCacheContents().get((setNum*8)+currSet.size());
                                }
                                currSet.add(i);
                            }
                            int z = 0;
                            for(JLabel temp: gui.getCacheContents()){
                                if (temp == replace){
                                    blockNumber = z%8;
                                    break;
                                }
                                z++;
                            }
                        }
                        memStatus =
                                "Sequence: " + ctr[0] +
                                        "  |  Address: " + i +
                                        "  |  Set: " + setNum +
                                        "  | Block: " + blockNumber +
                                        "  |  Status: " + isHit + "\n";
                        JLabel finalReplace = replace;
                        ctr[0]++;
                        if (isHit.equals("Miss"))
                            finalReplace.setText(String.valueOf(i));
                        gui.getMemText().append(memStatus);
                        updateEndText();
                        if (ctr[0] != memAddresses.length)
                            status = "In Progress";
                        else{
                            status = "Done";
                        }
                        updateInfoText("");
                        saveLog();
                    }
                    return null;
            }
        };
            w.execute();
            custom = false;


    }

    public void updateEndText(){
        int mac = sim.getNumHits()+sim.getNumMiss();
        String text = "  1. memory access count: " + mac +
                "\n  2. cache hit count: " + sim.getNumHits() +
                "\n  3. cache miss count: " + sim.getNumMiss() +
                "\n  4. cache hit rate: " + (float) sim.getNumHits()/mac +
                "\n  5. cache miss rate: " + (float) sim.getNumMiss()/mac+
                "\n  6. average memory access time: " + sim.getAvgAccessTime()+
                "\n  7. total memory access time: " + sim.getTotalAccessTime();
        gui.getEndText().setText(text);

    }
    public void saveLog() throws IOException {
        File log = new File("log.txt");
        String text = "Test type: " + testType + "\n" + gui.getMemText().getText() + "\n\n" + gui.getEndText().getText();
        Files.writeString(Path.of(log.getAbsolutePath()), text);

    }

    public void customAddresses(){
        customAdd = gui.getCustomAdd().getText();
        addresses = customAdd.split(",");
        updateInfoText("Custom");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        a = e.getActionCommand();
        switch (a){
            case "start":
                typeSelected = false;
                status = "Started";
                updateInfoText("");
                try {
                    startSim();

                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                gui.getStartBtn().setEnabled(false);
                gui.getSeqBtn().setEnabled(false);
                gui.getRandBtn().setEnabled(false);
                gui.getRepeatBtn().setEnabled(false);
                break;
            case "reset":
                showTrace = false;
                gui.getTrackBtn().setSelected(false);
                typeSelected = false;
                simType = "Sequential Test";
                status = "Not Started";
                gui.getMemText().setText("");
                sim.resetSim();
                for(JLabel l: gui.getCacheContents()){
                    l.setText("");
                }
                gui.getEndText().setText("");
                updateInfoText(a);
                break;
            default:
                simType = a;
                typeSelected = true;
                updateInfoText("");

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();

        // if selected print selected in console
        if (state == ItemEvent.SELECTED) {
            System.out.println("Selected");
            showTrace = true;
        }
        else {
            // else print deselected in console
            System.out.println("Deselected");
            showTrace = false;
        }
        updateInfoText("");
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        custom = true;
        customAddresses();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}

class Driver{
    public static void main(String[] args){
        CacheController controller = new CacheController(new CacheSim(), new CacheGUI());
    }
}


