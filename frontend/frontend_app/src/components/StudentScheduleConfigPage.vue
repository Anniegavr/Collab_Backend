<template>
  <div id="app">
    <div class="admin-panel" onload="window.location.reload()">
      <SearchField :search-term.sync="searchTerm"></SearchField>
      <table class="common_table">
        <thead>
        <tr>
          <th><span>Name</span></th>
          <th><span>Year</span></th>
          <th><span>Email</span></th>
          <th><span>Specialty</span></th>
          <th><span>Free Time</span></th>
          <th><span>Trip to/from College</span></th>
          <th><span>Action</span></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="studentGroup in filteredStudentGroups">
          <td>{{studentGroup.name}}</td>
          <td>{{studentGroup.year}}</td>
          <td>{{studentGroup.email}}</td>
          <td>{{studentGroup.specialty}}</td>
          <td>{{studentGroup.freeTime}}</td>
          <td>{{studentGroup.tripTime}}</td>
          <td>
            <button class="edit-btn" @click="editStudentGroup(studentGroup)">Edit</button>
            <button class="delete-btn" @click="deleteStudentGroup(studentGroup)">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
      <button class="addRecord" @click="addStudentGroup">+</button>
    </div>
  </div>
</template>

<script>
import SearchField from "./SearchField.vue";
import SearchIcon from "./SearchIcon.vue";
import axios from "axios";
import {Group} from "../models/Group.ts";

export default {
  name: "StudentScheduleConfig",
  components: {SearchField, SearchIcon},
  schedules: [],
  data() {
    return {
      schedules: this.fetchSchedules(),
      searchTerm: ''
    }
  },
  computed: {
    filteredschedules() {
      if (this.searchTerm) {
        console.log(this.schedules.filter((studentGroup) =>
            studentGroup.name.toLowerCase()));
        const index = this.schedules.indexOf((studentGroup) =>
            studentGroup.name.toLowerCase().includes(this.searchTerm.toLowerCase())
        )
        return this.schedules[index]
      } else {
        return this.schedules
      }
    }
  },
  methods: {
    fetchSchedules() {
      axios.get('http://localhost:8081/admin/schedules')
          .then(response => {
            this.schedules = response.data;
          })
          .catch(error => {
            console.log(error);
          });
    },
    editStudentGroup(schedule) {
      // Find the index of the user to edit
      const index = this.schedules.indexOf(schedule);
      // If the user is found
      if (index !== -1) {
        const newFreeTime = prompt('Enter new free time: ', schedule.freeTime)
        // If the user entered a new name and email
        if (newFreeTime) {
          axios.put("http://localhost:8081/admin/student_groups/edit/"+schedule.groupId, newFreeTime)
              .then(response => {
                this.schedules[index] = this.fetchSchedules();
                console.log("Modified types: ".concat(response.data))
              })
              .catch(error => {
                this.schedules[index] = newStudentGroup
                alert("Success")
                console.log(error)
              })

        }
      }
    },
    deleteStudentGroup(studentGroup) {
      // Find the index of the user to delete
      const index = this.schedules.findIndex(studentGroup.email === studentGroup.id);
      // If the user is found
      if (index !== -1) {
        // Prompt the user to confirm the deletion
        const confirmed = confirm(`Are you sure you want to delete ${studentGroup.name}?`);
        // If the user confirms the deletion
        if (confirmed) {
          axios.delete("http://localhost:8081/admin/schedules/delete/"+ studentGroup.id)
              .then(response => {
                this.schedules = this.fetchSchedules
                console.log("Modified types: ".concat(response.data))
              })
              .catch(error => {
                this.schedules.splice(index, 1)
                alert("Success")
                console.log(error)
              })

        }
      }
    },
  }
}
</script>

<style scoped>
@import './style/common_table.css';
</style>