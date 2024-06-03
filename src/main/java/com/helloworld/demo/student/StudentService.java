package com.helloworld.demo.student;

import java.util.concurrent.ExecutionException;
// import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

// @Service
public class StudentService {
    
    public String createStudent(Student student) throws ExecutionException, InterruptedException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("student").document(student.getId()).set(student);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public Student getStudent(String id) throws ExecutionException, InterruptedException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("student").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot docSnap = future.get();
        Student student;
        if(docSnap.exists()) {
            student = docSnap.toObject(Student.class);
            return student;
        }
        return null;
    }

    public String updateStudent(Student student) throws ExecutionException, InterruptedException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("student").document(student.getId()).set(student);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteStudent(String id) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection("student").document(id).delete();
        return "Successfully deleted " + id;
    }
}
