/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class ConfirmCash extends EntityTable{
    
    private int historyId;
    
    public ConfirmCash(int historyId){
	setType("ConfirmCash");
	this.historyId = historyId;
    }
    
    public int getHistoryId(){
	return historyId;
    }
}
