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
          <th><span>Specialty</span></th>
          <th><span>Action</span></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="teacher in fetchedTeachers">
          <td>{{ teacher.firstName }}</td>
          <td>{{ teacher.lastName }}</td>
          <td>{{ teacher.email }}</td>
          <td>{{ teacher.specialty }}</td>
          <td>
            <button class="edit-btn" @click="editTeacher(teacher)">Edit</button>
            <button class="delete-btn" @click="deleteTeacher(teacher)">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
      <button class="addRecord" @click="addTeacher">+</button>
    </div>
  </div>
</template>

<script>
import SearchField from "./SearchField.vue";
import SearchIcon from "./SearchIcon.vue";
import axios from "axios";
import { Teacher } from "../models/Teacher.ts";
import {useToast} from "vue-toastification";

export default {
  name: "TeachersPage",
  components: { SearchField, SearchIcon },
  fetchedTeachers: [],
  data() {
    return {
      fetchedTeachers: [],
      searchTerm: "",
    };
  },
  mounted() {
    this.fetchTeachers();
  },
  methods: {
    fetchTeachers() {
      axios
          .get("http://localhost:8081/admin/teachers")
          .then((response) => {
            console.log(response.data)
            this.fetchedTeachers = response.data;
            return response.data;
          })
          .catch((error) => {
            console.log(error);
          });
    },
    editTeacher(teacher) {
      // Find the index of the teacher to edit
      const index = this.fetchedTeachers.findIndex((s) => s.email === teacher.email);

      // If the teacher is found
      if (index !== -1) {
        // Prompt the user to enter the new details
        console.log(teacher);
        const newFirstName = prompt('Enter the new firstname:', teacher.firstName);
        const newLastName = prompt('Enter the new lastname:', teacher.lastName);
        const newEmail = prompt('Enter the new email:', teacher.email);
        const newSpecialty = prompt('Enter the new specialty:', teacher.specialty);

        // If the user entered new details
        if (
            newFirstName.trim() !== '' &&
            newLastName.trim() !== '' &&
            newEmail.trim() !== '' &&
            newSpecialty.trim() !== ''
        ) {
          console.log("Proceed with teacher to edit")
          const teacherToEdit = {
            firstName: newFirstName,
            lastName: newLastName,
            email: newEmail,
            specialty: newSpecialty,
          };

          // Update the teacher object with the new details
          axios
              .post('http://localhost:8081/admin/update_teacher/' + teacher.email, teacherToEdit)
              .then(response => {
                this.fetchTeachers();
                console.log('Modified types: '.concat(response.data));
                const toast = useToast();
                toast.success('Teacher updated successfully');
              })
              .catch(error => {
                this.fetchedTeachers[index] = teacherToEdit;
                alert('Success');
                console.log(error);
              });

          this.fetchTeachers();
        } else {
          alert('Error: All fields must have a value');
        }
      }
    },
    deleteTeacher(teacher) {
      const index = this.fetchedTeachers.findIndex((s) => s.email === teacher.email);
      if (index !== -1) {
        console.log("Deleting teacher "+teacher.email)
        const confirmed = confirm(`Are you sure you want to delete ${teacher.firstName} ${teacher.lastName} ?`);
        if (confirmed) {
          axios.delete("http://localhost:8081/admin/delete_teacher/"+ teacher.email)
              .then(response => {
                this.fetchTeachers()
                console.log("Modified types: ".concat(response.data))
                const toast = useToast();
                toast.success('Teacher deleted successfully');
              })
              .catch(error => {
                const index = this.fetchedTeachers.indexOf(teacher)
                this.fetchedTeachers.splice(index, 1)
                alert("Success")
                console.log(error)
              })

        }
      }
    },
    addTeacher() {
      const firstName = prompt('Enter the firstname:');
      const lastName = prompt('Enter the lastname:');
      const email = prompt('Enter the email:');
      const specialty = prompt('Enter the specialty:');

      // Check if all fields have letters
      if (
          firstName.trim() !== '' &&
          lastName.trim() !== '' &&
          email.trim() !== '' &&
          specialty.trim() !== ''
      ) {
        const newTeacher = {
          firstName: firstName,
          lastName: lastName,
          email: email,
          specialty: specialty,
        };

        axios
            .post('http://localhost:8081/admin/add_teacher', newTeacher)
            .then(response => {
              this.fetchTeachers();
              console.log('Added teacher: '.concat(response.data));
              const toast = useToast();
              toast.success('Teacher added successfully');
            })
            .catch(error => {
              this.fetchedTeachers.push(newTeacher);
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