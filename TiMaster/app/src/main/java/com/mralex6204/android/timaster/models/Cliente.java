package com.mralex6204.android.timaster.models;

import com.mralex6204.android.timaster.db.Column;
import com.mralex6204.android.timaster.db.Model;

import java.util.ArrayList;

/**
 * Created by oavera on 20/10/16.
 */

public class Cliente extends Model {

    private final Column Id = new Column("_Id", Column.Type.INTEGER);
    public final Column ClienteId = new Column("ClienteId", Column.Type.INTEGER,true);
    public final Column FullName = new Column("Name", Column.Type.TEXT);
    public final Column Email = new Column("Email", Column.Type.TEXT);
    private final ArrayList<TicketInfo> Tickets = new ArrayList<TicketInfo>();

    public Cliente(){
        super("TblCliente");
        this.onBuild();
    }

    @Override
    protected void onBuild() {

        this.Id.setAutoIncrement(true);

        this.Columns.add(this.Id);
        this.Columns.add(this.ClienteId);
        this.Columns.add(this.FullName);
        this.Columns.add(this.Email);

    }

    public ArrayList<TicketInfo> getTickets() {
        return Tickets;
    }

    public boolean addTicket(TicketInfo Ticket){
        return Tickets.add(Ticket);
    }

    public boolean setTicket(TicketInfo Ticket) {
        int idx  = Tickets.indexOf(Ticket);

        if(idx >-1){
            Tickets.set(idx,Ticket);
            return true;
        }

        return false;
    }

    public  boolean  removeTicket(TicketInfo Ticket) {
        int idx = Tickets.indexOf(Ticket);

        if(idx > -1){
            Tickets.remove(idx);
            return true;
        }

        return false;
    }

    public int getClienteId() {
        return (int) ClienteId.Value;
    }

    public void setClienteId(int clienteId) {
        ClienteId.Value = clienteId;
    }

    public String getFullName() {
        return (String) FullName.Value;
    }

    public void setFullName(String fullName) {
        FullName.Value = fullName;
    }

    public String getEmail() {
        return (String) Email.Value;
    }

    public void setEmail(String email) {
        Email.Value = email;
    }


}
