package com.test.blockchain;

import java.util.ArrayList;
import com.test.helper.SHA256;

public class Block {
    private final int index;

    private final String previousBlockHash;

    private final long timestamp;

    private final ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    private String hash;

    private int nonce = 0;

    public Block(int index, String previousBlockHash, long timestamp) {
        this.index = index;
        this.previousBlockHash = previousBlockHash;
        this.timestamp = timestamp;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactionList;
    }

    public int getIndex() {
        return this.index;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getPreviousHash() {
        return this.previousBlockHash;
    }

    public String getHash() {
        return this.hash;
    }

    public String computeHash() {
        String data = this.previousBlockHash + Long.toString(this.timestamp);

        for (Transaction trx: this.transactionList) {
            data += trx.toString();
        }

        data += Integer.toString(this.nonce);

        return SHA256.hash(data);
    }

    public void proofOfWork() {
        do {
            this.nonce++;
            this.hash = this.computeHash();
        } while (!this.checkHash());
    }

    private boolean checkHash() {
        short difficulty = Blockchain.getInstance().getMiningDifficulty();

        StringBuilder target = new StringBuilder();
        for (int i = 0; i < difficulty; i++) {
            target.append('0');
        }
        return this.hash.startsWith(target.toString());
    }
}
