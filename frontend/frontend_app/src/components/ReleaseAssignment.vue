<template>
  <div class="form-container">
    <div class="form-assign">

      <div class="warning-message" v-if="showWarning">
        <p>Please fill in all fields before submitting.</p>
      </div>
      <div class="form-group">
        <h2 class="form_title">Enter Details</h2>
        <label for="assignment-course" class="rel_label">Course:</label>
        <br />
        <input type="text" id="assignment-course" class="assign_inp_course" v-model="assignment.course" />
      </div>
      <div class="form-group">
        <label for="assignment-title" class="rel_label">Title:</label>
        <br />
        <input type="text" id="assignment-title" class="assign_inp_title" v-model="assignment.title" />
      </div>
      <div class="form-group">
        <label for="assignment-description" class="rel_label">Description:</label>
        <br />
        <textarea id="assignment-description" v-model="assignment.description" class="assign_descr"></textarea>
      </div>
      <div class="form-group">
        <label for="assignment-group" class="rel_label">Group:</label>
        <select id="assignment-group" v-model="assignment.group">
          <option value="" class="option">Choose group</option>
          <option v-for="group in groups" :value="group" class="option">{{ group }}</option>
        </select>
      </div>
      <div class="form-group">
        <label for="assignment-type" class="rel_label">Type:</label>
        <select id="assignment-type" v-model="assignment.type">
          <option value="" class="option">Choose type</option>
          <option v-for="type in types" :value="type" class="option">{{ type }}</option>
        </select>
      </div>
      <div class="form-group">
        <label for="assignment-time" class="rel_label">Estimated Time:</label>
        <input type="text" id="assignment-time" v-model="assignment.time" :placeholder="timeHint" class="est_time" />
      </div>
      <div class="form-group">
        <label for="assignment-teacher" class="rel_label">Teacher:</label>
        <br />
        <input type="text" id="assignment-teacher" class="assign_inp_teacher" v-model="assignment.teacherName" />
      </div>
      <div class="form-group">
        <label for="assignment-date" class="rel_label">Due Date:</label>
        <div class="current-time">{{ formattedDate }}</div>
      </div>
      <div class="button-group">
        <button class="refresh-button" @click="refreshForm">Refresh</button>
        <button @click="submit" class="submit_button">Submit</button>
      </div>
    </div>
    <div class="calendar-container" id="calendar_assign">
      <div class="calendar">
        <VDatePicker
            v-model="assignment.dueDate"
            @change="setDate"
            :date-format="'M/D/YYYY'"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { Calendar } from 'v-calendar';
import 'v-calendar/style.css';
import axios from 'axios';
import { useToast } from 'vue-toastification';
import 'vue-toastification/dist/index.css';

export default {
  name: "ReleaseAssignment",
  components: {
    Calendar
  },
  setup() {
    const toast = useToast();
    const assignment = ref({
      course: '',
      title: '',
      description: '',
      group: '',
      type: '',
      time: '',
      dueDate: null,
      teacherName: '',
    });

    const groups = ["FAF-191", "FAF-192", "FAF-193"];
    const types = ["LAB", "PROJECT", "READING"];
    const timeHint = "e.g. 2";

    const currentTime = new Date();
    const formattedDate = computed(() => {
      const dateObj = assignment.value.dueDate;
      if (dateObj) {
        const month = dateObj.getMonth() + 1;
        const day = dateObj.getDate();
        const year = dateObj.getFullYear();
        return `${month}/${day}/${year}`;
      }
      return '';
    });

    const showWarning = ref(false);

    function refreshForm() {
      assignment.value = {
        course: '',
        title: '',
        description: '',
        group: '',
        type: '',
        time: '',
        dueDate: null,
        teacherName: '',
      };
    }

    function submit() {
      if (
          assignment.value.course &&
          assignment.value.title &&
          assignment.value.description &&
          assignment.value.group &&
          assignment.value.type &&
          assignment.value.time &&
          assignment.value.dueDate &&
          assignment.value.teacherName
      ) {
        axios
            .post('http://localhost:8081/assignments/new', assignment.value)
            .then(response => {
              const assignmentDTO = response.data;
              if (assignmentDTO.title === "NoSuitableSchedule") {
                toast.warning(assignmentDTO.description, { timeout: 5000, closeOnClick: true });
              } else {
                toast.success('Assignment released', { timeout: 2000, closeOnClick: true });
              }
              console.log(response.data);
              refreshForm();
            })
            .catch(error => {
              const errorMessage = error.response ? error.response.data : 'Unknown error';
              toast.error(`Could not release assignment. Review the information and retry.`, { timeout: 5000, closeOnClick: true });
              console.error(error);
            });
      } else {
        showWarning.value = true;
      }
    }

    return {
      assignment,
      groups,
      types,
      timeHint,
      currentTime,
      showWarning,
      refreshForm,
      submit,
      formattedDate,
    };
  },
};
</script>

<style>
@import "./style/general_styles.css";
.form_title {
  display: flex;
  position: center;
  margin-bottom: 0;
  margin-top: 16vh;
  margin-left: 10vw;
}
.form-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 70vw;
  height: 50vh;
}
.form-group {
  flex-direction: column;
}
.assign_inp_title {
  border-radius: 12px;
  height: 4vh;
  width: 15vw;
}
.assign_inp_course {
  border-radius: 12px;
  height: 4vh;
  width: 15vw;
}
.assign_inp_teacher {
  border-radius: 12px;
  height: 4vh;
  width: 15vw;
}
.form-assign {
  width: 50vw;
}
.rel_label {
  font-size: 2.6vh;
  font-weight: bold;
  top: 0;
}
.assign_descr {
  height: 12vh;
  border-radius: 12px;
  width: 20vw;
  padding: 1vh;
}
.est_time {
  border-radius: 12px;
  height: 4vh;
  width: 7vw;
  margin-left: 1svw;
  padding-left: 5px;
}
#calendar_assign {
  top: 8rem;
  display: flex;
  margin-right: 9vw;
  margin-left: 2vh;
}

.submit_button {
  margin-left: 13vw;
  display: flex;
  position: center;
  margin-top: 0;
}
.refresh-button {
  margin-left: 1vw;
  margin-bottom: 0;
}
.select {
  margin-left: 1vw;
  height: 2.7vh;
  outline: #2c3e50;
  border-radius: 3vw;
  padding: 1px;
}
</style>