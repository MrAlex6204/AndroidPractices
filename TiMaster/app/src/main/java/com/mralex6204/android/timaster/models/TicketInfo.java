package com.mralex6204.android.timaster.models;

/**
 * Created by oavera on 20/10/16.
 */

import com.mralex6204.android.timaster.db.Column;
import com.mralex6204.android.timaster.db.Model;

import java.util.Date;

public class TicketInfo extends Model {

    public Column Id = new Column("Id", Column.Type.INTEGER,true);
    public Column ClienteId = new Column("ClienteId", Column.Type.INTEGER);
    public Column Fecha = new Column("Fecha", Column.Type.TIME_STAMP,"CURRENT_TIMESTAMP");

    public TicketInfo(){
        super("TblTickets");
        this.onBuild();
    }

    protected void onBuild() {
        this.Columns.add(this.Id);
        this.Columns.add(this.ClienteId);
        this.Columns.add(this.Fecha);
    }

    public int getId() {
        return (int)Id.Value;
    }

    public void setId(int id) {
        Id.Value = id;
    }

    public int getClienteId() {
        return (int)ClienteId.Value;
    }

    public void setClienteId(int clienteId) {
        ClienteId.Value = clienteId;
    }

    public Date getFecha() {
        return (Date)Fecha.Value;
    }

    public void setFecha(Date fecha) {
        Fecha.Value = fecha;
    }

}
