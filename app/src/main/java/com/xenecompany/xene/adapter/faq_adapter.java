package com.xenecompany.xene.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xenecompany.xene.R;
import com.xenecompany.xene.model.faq_model;

import java.util.ArrayList;

public class faq_adapter extends RecyclerView.Adapter<faq_adapter.ViewHolder> {
    final boolean[] flagAnsShowing;
    private ArrayList<faq_model> faq;
    public faq_adapter(ArrayList<faq_model> faq){
        this.flagAnsShowing = new boolean[faq.size()];
        this.faq=faq;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_faq_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        flagAnsShowing[position] = false;
        holder.question.setText(faq.get(position).qs);
        holder.faqDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagAnsShowing[position]) {
                    flagAnsShowing[position] =true;
                    holder.t1.setText("Ans"+position);
                    holder.ans.setText(faq.get(position).ans);
                }
                else{
                    flagAnsShowing[position] =false;
                    holder.t1.setText(null);
                    holder.ans.setText(null);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return faq.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected final TextView question,ans,t1;
        protected ConstraintLayout faqDataLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.faqQuestion);
            ans = itemView.findViewById(R.id.faqAns);
            t1= itemView.findViewById(R.id.faqTextAns);
            faqDataLayout = itemView.findViewById(R.id.faqDataLayout);
        }
    }
}
