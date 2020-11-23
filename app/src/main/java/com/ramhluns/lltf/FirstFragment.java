package com.ramhluns.lltf;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Strings;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.BreakIterator;
import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.MODE_PRIVATE;

public class FirstFragment extends Fragment {

    EditText hming, section, phone, chhuahduhchhan, chhuahduhna, chhuahni, chhuahhnuDarkar, motorHming, motorNumber;
    TextView tvHming, tvSection, tvPhone, tvChhuahduhchhan, tvChhuahduhna, tvChhuahni, tvChhuahhnuDarkar, tvMotorHming, tvMotorNumber;
    Button submit;
    LinearLayout formLayout;
    RelativeLayout hriatpuinaLayout;
    GifImageView gifStatus;
    ImageView ivSignature;
    ImageView ivReject;
    TextView scrollingText;

    SharedPreferences mPref;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        mPref = container.getContext().getSharedPreferences(getString(R.string.my_pref), MODE_PRIVATE);

        String docId = mPref.getString(getString(R.string.document_id), null);

        formLayout = view.findViewById(R.id.formLayout);
        hriatpuinaLayout = view.findViewById(R.id.hriatpuinaLayout);

        formLayout.setVisibility(View.VISIBLE);
        hriatpuinaLayout.setVisibility(View.GONE);

        hming = view.findViewById(R.id.hming);
        section = view.findViewById(R.id.section);
        phone = view.findViewById(R.id.phone);
        chhuahduhchhan = view.findViewById(R.id.chhuahduhchhan);
        chhuahduhna = view.findViewById(R.id.chhuahduhna);
        chhuahni = view.findViewById(R.id.chhuahni);
        chhuahhnuDarkar = view.findViewById(R.id.chhuahhundarkar);
        motorHming = view.findViewById(R.id.motorhming);
        motorNumber = view.findViewById(R.id.motor_number);
        submit = view.findViewById(R.id.submit);
        tvHming = view.findViewById(R.id.tvHming);
        tvSection = view.findViewById(R.id.tvSection);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvChhuahduhchhan = view.findViewById(R.id.tvChhuahduhchhan);
        tvChhuahduhna = view.findViewById(R.id.tvChhuahduhna);
        tvChhuahni = view.findViewById(R.id.tvDate);
        tvChhuahhnuDarkar = view.findViewById(R.id.tvChhuahhnuDarkar);
        tvMotorHming = view.findViewById(R.id.tvMotorHming);
        tvMotorNumber = view.findViewById(R.id.tvMotorNo);
        gifStatus = view.findViewById(R.id.gifStatus);
        ivSignature = view.findViewById(R.id.ivSignature);
        ivReject = view.findViewById(R.id.ivReject);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSubmit();
            }
        });


        if (!Strings.isNullOrEmpty(docId)) {
            checkDocument(docId);
        }

        return view;


    }

    private void checkDocument(String docId) {
        formLayout.setVisibility(View.GONE);
        hriatpuinaLayout.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forms").document(docId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (document != null) {
                    Log.d("FORM", document.getId() + " => " + document.getData());
                    FormModel formData = document.toObject(FormModel.class);
                    if (formData.hming != null) {
                        tvHming.setText(formData.hming);
                        tvSection.setText(formData.section);
                        tvPhone.setText(formData.phone);
                        tvChhuahduhchhan.setText(formData.chhuahduhchhan);
                        tvChhuahduhna.setText(formData.chhuahduhna);
                        tvChhuahni.setText(formData.chhuahni);
                        tvChhuahhnuDarkar.setText(formData.chhuahhnuDarkar);
                        tvMotorHming.setText(formData.motorHming);
                        tvMotorNumber.setText(formData.motorNumber);
                        gifStatus.setVisibility(formData.status.equalsIgnoreCase("pending") ? View.VISIBLE : View.GONE);
                        ivSignature.setAlpha(formData.status.equalsIgnoreCase("approved") ? 1f : 0f);
                        ivReject.setVisibility(formData.status.equalsIgnoreCase("reject") ? View.VISIBLE : View.GONE);
                    }
                } else {
                    Log.w("FORM", "Error getting documents.", e);
                    formLayout.setVisibility(View.VISIBLE);
                    hriatpuinaLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void performSubmit() {
        if (hming.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ||
                chhuahduhchhan.getText().toString().isEmpty() || chhuahduhna.getText().toString().isEmpty() ||
                chhuahni.getText().toString().isEmpty() || chhuahhnuDarkar.getText().toString().isEmpty() ||
                motorHming.getText().toString().isEmpty() || motorNumber.getText().toString().isEmpty()) {
            showAlert(getString(R.string.empty_form_msg), true);
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.submitting));
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        Map<String, Object> form = new HashMap<>();
        form.put("hming", hming.getText().toString());
        form.put("section", section.getText().toString());
        form.put("phone", phone.getText().toString());
        form.put("chhuahduhchhan", chhuahduhchhan.getText().toString());
        form.put("chhuahduhna", chhuahduhna.getText().toString());
        form.put("chhuahni", chhuahni.getText().toString());
        form.put("chhuahhnuDarkar", chhuahhnuDarkar.getText().toString());
        form.put("motorHming", motorHming.getText().toString());
        form.put("motorNumber", motorNumber.getText().toString());
        form.put("status", "pending");
        form.put("created", Timestamp.now());

        db.collection("forms")
                .add(form)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showAlert(getString(R.string.submit_success_msg), false);
                        progressDialog.dismiss();

                        mPref.edit().putString(getString(R.string.document_id), documentReference.getId()).apply();
                        checkDocument(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FORM", "Error adding document", e);
                        showAlert(e.getMessage(), false);
                        progressDialog.dismiss();
                    }
                });
    }

    private void showAlert(String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setCancelable(cancelable);

        builder.setPositiveButton(
                "Awle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}