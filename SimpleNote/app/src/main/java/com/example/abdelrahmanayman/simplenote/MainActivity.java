package com.example.abdelrahmanayman.simplenote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    RelativeLayout relativeLayout ;
    private DrawerLayout drawerLayout ;
    ArrayList<ListViewItems>listViewItems = new ArrayList<ListViewItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        //////////////////////////////////////  Side Menu  /////////////////////////////////////////////////
        listViewItems.add(  new ListViewItems("Play Musik" , R.drawable.ic_library_music_black_24dp));

        drawerLayout = findViewById(R.id.MyDrawable);
        relativeLayout = findViewById(R.id.SideMenuContent);
        listView = findViewById(R.id.lvSide);
        DrawerListAdapter adapter = new DrawerListAdapter(this , listViewItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectItemFromDrawer(position);
            }
        });
        ///////////////////////////////////////////  Floating Button + Add New Note  ////////////////////////////////////////////
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newNoteIntent = new Intent(MainActivity.this , NewNoteActivity.class);
                startActivity(newNoteIntent);
            }
        });
        Toast.makeText(MainActivity.this, "Long Press On Note To Delete ", Toast.LENGTH_LONG).show();
        DisplayNewNote();
        ///////////////////////////////  Delete Note ////////////////////////////////////////////////////////////////

        final ListView noteItemsListView =findViewById(R.id.lvNotes);

        noteItemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DBConnection dBconnection = new DBConnection(MainActivity.this);
                ArrayList<NoteItem> arrayList = dBconnection.getAllNotes();
                MyCustomAdapter myCustomAdapter = new MyCustomAdapter(arrayList);
                noteItemsListView.setAdapter(myCustomAdapter);
                Integer id1 = Integer.parseInt(arrayList.get(position).getId());
                DeleteNote(id1);
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        ///////////////////////////////////////   Edit Note  ////////////////////////////////////////////////////////////////////
                noteItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DBConnection dBconnection = new DBConnection(MainActivity.this);
                        ArrayList<NoteItem> arrayList = dBconnection.getAllNotes();
                        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(arrayList);
                        noteItemsListView.setAdapter(myCustomAdapter);
                        String noteId = arrayList.get(position).getId();
                        String date = arrayList.get(position).getDate();
                        String NoteText = arrayList.get(position).getNotetext();
                        Intent intent = new Intent(MainActivity.this , EditNoteActivity.class);
                        intent.putExtra("id" , noteId);
                        intent.putExtra("NoteText" , NoteText);
                        startActivity(intent);
                    }
                });
                ////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void DisplayNewNote() {
        //////////////////////   getting value from NewNoteActivity and pass it to arrayList ///////////////////////////////////////
        ListView noteItemsListView =findViewById(R.id.lvNotes);
        registerForContextMenu(noteItemsListView);
        DBConnection dBconnection = new DBConnection(MainActivity.this);
        ArrayList<NoteItem> noteItemArrayList =dBconnection.getAllNotes();
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(noteItemArrayList);
        noteItemsListView.setAdapter(myCustomAdapter);
    }

    public void DeleteNote(Integer id ){
        ListView listView = findViewById(R.id.lvNotes);
        DBConnection dBconnection = new DBConnection(this);
        dBconnection.deleteNote(id);
        ArrayList<NoteItem> arrayList = dBconnection.getAllNotes();
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(arrayList);
        listView.setAdapter(myCustomAdapter);
    }

    private void SelectItemFromDrawer(int position) {

            if (position==0) {
                Fragment fragment = new MusikMp3Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.MainContent, fragment).commit();
            }
            listView.setItemChecked(position, true);
            setTitle(listViewItems.get(position).Title);

            drawerLayout.closeDrawer(relativeLayout);

    }

    class MyCustomAdapter extends BaseAdapter{

    ArrayList<NoteItem> noteItemArrayList = new ArrayList<NoteItem>();

    public MyCustomAdapter(ArrayList<NoteItem> noteItemArrayList) {
        this.noteItemArrayList = noteItemArrayList;
    }

    @Override
    public int getCount() {
        return noteItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteItemArrayList.get(position).getNotetext();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.note_design , null);

        TextView textViewNote = view.findViewById(R.id.tvTextNote);
        TextView textViewNDate = view.findViewById(R.id.tvDateAndtime);
        textViewNDate.setText(noteItemArrayList.get(position).getDate());
        textViewNote.setText(noteItemArrayList.get(position).getNotetext());
        return view ;
    }


}
}