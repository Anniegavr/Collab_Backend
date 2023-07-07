<template>
  <div id="app">
    <div class="page_elements">
      <div class="profile_grid">
        <div class="left_part" style="margin-top: 12vh; z-index: -1">
          <img src="./assets/profile_img.png" class="profile_img" style="position: static;max-width: 60vh"/>
        </div>

        <div class="right_part">
          <div class="profile_info">
            <h1 style="max-font-size: 1.5svh;">Anastasia Gavrilita</h1>
            <h2>Student, job: part-time, not public</h2>
            <h2>FAF-191</h2>
            <h2>Year: 4</h2>
          </div>
        </div>
      </div>
      <div class="achievements">
        <h2 style="color: #2c2049">Achievements</h2>
        <div class="ach_grid" v-for="achievement in achievementData" :key="skillType">
          <h3 class="ach_type" style="display: inline-flex">{{ achievement.skillType }}</h3>
          <h3 class="ach_descr" style="display: inline-flex">{{ achievement.studentAccomplishment }}</h3>
        </div>
      </div>
    </div>
  </div>
  <button class="addRecord" @click="addRecord">+</button>
</template>

<script>
import AssignmentsProgress from "./AssignmentsProgress.vue";
import HorizontalChart from "./HorizontalChart.vue";
import axios from "axios";
import DonutChart from "./DonutChart.vue";
import {useToast} from "vue-toastification";

export default {
  name: "ProfilePage",
  components: {DonutChart, HorizontalChart, AssignmentsProgress },
  data() {
    return {
      timer: null,
      achievementData: {},
      studentId: null
    };
  },
  mounted() {
    this.timer = setInterval(() => {
      this.bars.forEach(bar => (bar.value = 25 + Math.random() * 75));
    }, 2000);
    this.fetchAchievements();
  },
  methods: {
    fetchAchievements() {
      let studentId = localStorage.getItem("userId")
      this.studentId = studentId;
      axios.get("http://localhost:8081/students/"+studentId+"/accomplishments")
          .then(response => {
            this.achievementData = response.data;
          })
          .catch(error => {
            console.error("Error:", error);
          });
    },
    addRecord() {
      const skillType = prompt('Enter the skill type:');
      const description = prompt('Describe the achievement:');
      if (
          skillType.trim() !== '' &&
          description.trim() !== ''
      ) {
        const newSkill = {
          studentId: localStorage.getItem("userId"),
          skillType: skillType,
          studentAccomplishment: description,
        };
        axios
            .post('http://localhost:8081/students/'+localStorage.getItem("userId")+'/add_skill', newSkill)
            .then(response => {
              const toast = useToast();
              toast.success('Achievement added successfully');
              console.log('Added skill: '.concat(response.data));

              this.achievementData=this.fetchAchievements();
            })
            .catch(error => {
              this.studentGroups.push(newStudentGroup);
              alert('Success');
              console.log(error);
            });
      } else {
        const toast = useToast();
        toast.info("You must fill in all the fields.");
      }
    }
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  }
};
</script>

<style scoped>
.addRecord {
  color: rgba(97, 68, 173, 0.98);
  font-size: x-large;
  border-color: rgba(97, 68, 173, 0.98);
  border-radius: 15px;
  margin-top: 10vh;
  filter: drop-shadow(-5px 2px 5px rgba(0, 0, 0, 0.25));
}
.page_elements {
  display: flex;
  flex-direction: column;
  gap: 2vh;
  position: relative;
  top: 2vh;
}

.profile_grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 10vw;
  border-color: #2c3e50;
  position: static;
  align-items: center;
}

.profile_img {
  border-radius: 5vh;
  border-color: #dddddd;
  box-shadow: -9px 20px 20px 10px rgba(0, 0, 0, 0.25);
  display: flow;
  position: relative;
}


.achievements {
  top: 6vh;
  margin: auto;
  box-shadow: -9px 12px 18px 10px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  width: 50%;
  position: relative;
  justify-self: center;
}

.ach_grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 1vw;
  border-color: #005fc7;
  position: inherit;
  align-items: center;
}

.ach_type {
  justify-self: right;
  color: #2574c2;
  font-weight: bolder;
}

.ach_descr {
  justify-self: left;
  color: #3e964d;
}

h1 {
  max-font-size: 1.5svh;
}

h2 {
  margin: 0;
}

h3 {
  margin: 0;
}

ul {
  list-style-type: disc;
  margin: 0;
  padding-left: 2rem;
}
</style>
