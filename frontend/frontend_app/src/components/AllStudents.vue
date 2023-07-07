<template>
  <div id="app">
    <div class="admin-panel" onload="window.location.reload()">
        <SearchField :search-term.sync="searchTerm"></SearchField>
        <table class="common_table">
          <thead>
          <tr>
            <th><span>First Name</span></th>
            <th><span>Last Name</span></th>
            <th><span>Email</span></th>
            <th><span>Group</span></th>
            <th><span>Year</span></th>
            <th><span>Specialty</span></th>
            <th><span>Action</span></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="student in filteredStudents" :key="student.email">
            <td>{{student.firstName}}</td>
            <td>{{student.lastName}}</td>
            <td>{{ student.email }}</td>
            <td>{{student.group}}</td>
            <td>{{student.year}}</td>
            <td>{{student.specialty}}</td>
            <td>
              <button class="edit-btn" @click="editStudent(student)">Edit</button>
              <button class="delete-btn" @click="deleteStudent(student)">Delete</button>
            </td>
          </tr>
          </tbody>
        </table>
      <button class="addRecord" @click="addStudent">+</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import SearchIcon from "./SearchIcon.vue";
import SearchField from "./SearchField.vue";
import {Student} from "../models/Student.ts";
import { useToast } from 'vue-toastification';
export default {
  name: "AllStudents",
  components: {SearchField, SearchIcon},
  students: [],
  data() {
    return {
      students: this.fetchStudents(),
      searchTerm: ''
    }
  },
  computed: {
    filteredStudents() {
      if (this.searchTerm) {
        console.log(this.students.filter((student) =>
            student.firstName.toLowerCase()));
        return this.students.filter((student) =>
            student.firstName.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
      } else {
        return this.students;
      }
    }
  },
  methods: {
    fetchStudents() {
      axios.get('http://localhost:8081/students')
          .then(response => {
            this.students = response.data;
          })
          .catch(error => {
            console.log(error);
          });
    },
    editStudent(student) {
      // Find the index of the student to edit
      console.log(student.email)
      const index = this.students.findIndex((s) => s.email === student.email);
      // If the student is found
      if (index !== -1) {
        // Prompt the user to enter the new information
        const newFirstName = prompt('Enter the new firstname:', student.firstName);
        const newLastName = prompt('Enter the new lastname:', student.lastName);
        const newEmail = prompt('Enter the new email:', student.email);
        const newGroup = prompt('Enter new group:', student.group);
        const newSpecialty = prompt('Enter the new specialty:', student.specialty);
        const newYear = prompt('Enter the new year:', student.year);

        // If any of the fields have been changed
        if (
            newFirstName !== student.firstName ||
            newLastName !== student.lastName ||
            newEmail !== student.email ||
            newGroup !== student.group ||
            newSpecialty !== student.specialty ||
            newYear !== student.year
        ) {
          // Update the student object with the new information
          this.students[index].firstName = newFirstName;
          this.students[index].lastName = newLastName;
          this.students[index].email = newEmail;
          this.students[index].specialty = newSpecialty;
          this.students[index].group = newGroup;
          this.students[index].year = newYear;
          const studentToEdit = {
            firstName: newFirstName,
            lastName: newLastName,
            email: newEmail,
            group: newGroup,
            specialty: newSpecialty,
            year: newYear
          };

          axios
              .put('http://localhost:8081/admin/update_student/' + student.email, studentToEdit)
              .then(response => {
                this.students = this.fetchStudents();
                console.log(response.status + '\n' + response.data);
                const toast = useToast();
                toast.success('Student updated successfully');
              })
              .catch(error => {
                console.log(error);
              });
        }
      }
    }
,
    deleteStudent(student) {
      const index = this.students.findIndex((s) => s.email === student.email);
      if (index !== -1) {
        const confirmed = confirm(`Are you sure you want to delete ${student.firstName} ${student.lastName} ?`);
        if (confirmed) {
          console.log("aha, id "+student.id)
          axios.delete('http://localhost:8081/admin/del_student/'+ student.email)
              .then(response => {
                this.students = this.fetchStudents()
                console.log(response.status+"\n "+response.data)
                const toast = useToast();
                toast.success('Student deleted successfully');
              })
              .catch(error => {
                this.students.splice(index, 1);
                alert("Success")
                console.log(error);
              });
        }
      }
    },
    addStudent() {
      const firstName = prompt('Enter the firstname:');
      const lastName = prompt('Enter the lastname:');
      const email = prompt('Enter the email:');
      const specialty = prompt('Enter the specialty:');
      const group = prompt('Enter the group:');
      const year = prompt('Enter the year:');

      // Check if all fields have letters
      if (
          firstName.trim() !== '' &&
          lastName.trim() !== '' &&
          email.trim() !== '' &&
          specialty.trim() !== '' &&
          group.trim() !== '' &&
          year.trim() !== ''
      ) {
        const newStudent = {
          firstName: firstName,
          lastName: lastName,
          group: group,
          email: email,
          specialty: specialty,
          year: year,
        };

        axios
            .post('http://localhost:8081/admin/add_student', newStudent)
            .then(response => {
              this.students.push(response.data);
              console.log('Added student: '.concat(response.data));
              const toast = useToast();
              toast.success('Student added successfully');
            })
            .catch(error => {
              this.students.push(newStudent);
              alert('Success');
              console.log(error);
            });
      } else {
        alert('Error: All fields must contain letters');
      }
    }
  },
}
</script>

<style scoped>
@import './style/common_table.css';

</style>