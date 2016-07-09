package br.com.djun.catalogo;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends ActionBarActivity implements RecyclerView.OnItemTouchListener {
    private RecyclerView grid;
    private List<Book> books;
    private GestureDetectorCompat detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        grid = (RecyclerView)findViewById(R.id.grid);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        grid.setLayoutManager(manager);

        GridAdapter adapter	= new GridAdapter(createBooks());
        grid.setAdapter(adapter);

        detector = new GestureDetectorCompat(this, new GridGestureDetector());

        grid.addOnItemTouchListener(this);
    }

    private	List<Book>	createBooks() {
        books = new ArrayList<Book>();
        for (int i = 1; i <= 30; i++) {
            Book book = new Book("Title	" + i, "Author	" + i,
                    R.drawable.ic_cover);
            books.add(book);
        }
        return books;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        detector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static class GridViewHolder extends RecyclerView.ViewHolder	{
        protected ImageView image;
        protected TextView title;
        protected TextView author;

        private	GridViewHolder(View view) {
            super(view);
            image	=	(ImageView)	view.findViewById(R.id.image);
            title	=	(TextView)	view.findViewById(R.id.title);
            author	=	(TextView)	view.findViewById(R.id.author);
        }
    }

    public static class GridAdapter extends RecyclerView.Adapter<GridViewHolder>	{
        private	List<Book>	books;
        public	GridAdapter(List<Book>	books) {
            this.books	=	books;
        }
        @Override
        public	int	getItemCount() {
            return	books.size();
        }
        @Override
        public	GridViewHolder onCreateViewHolder(ViewGroup parent, int	viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.grid_item,parent,false);
            GridViewHolder holder = new GridViewHolder(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(GridViewHolder	viewHolder,	int	position) {
            Book book =	books.get(position);
            viewHolder.image.setImageResource(book.getImage());
            viewHolder.title.setText(book.getTitle());
            viewHolder.author.setText(book.getAuthor());
        }
    }
    private	class GridGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public	boolean	onSingleTapConfirmed(MotionEvent e)	{
            View v = grid.findChildViewUnder(e.getX(),e.getY());
            int	i=	grid.getChildPosition(v);
            Book book =	books.get(i);
            Toast.makeText(GridActivity.this,"Book"+book.getTitle(),Toast.LENGTH_SHORT).show();
            return	super.onSingleTapConfirmed(e);
        }
    }
}
