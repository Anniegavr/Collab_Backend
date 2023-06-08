<template>
  <div id="app" :data-theme="theme">
    <div id="login" @load="reloadPage">
      <h1>Welcome</h1>
      <div id="post">
        <form id="form-post">
          <input type="text" v-model="login" placeholder="Email or Username" name="email" id="email" class="email_input" />
          <input spellcheck="false" type="password" v-model="password" placeholder="Password" name="password" id="password" class="password_input" />
          <div style="display: flex; margin-top: 3vh;" id="roles">
            <div>
              <input type="radio"  value="STUDENT" name="userRole" id="student_role" />
              <label for="student_role">STUDENT</label>
            </div>

            <div>
              <input type="radio"  value="TEACHER" name="userRole" id="teacher_role" />
              <label for="teacher_role">TEACHER</label>
            </div>

            <div>
              <input type="radio"  value="ADMIN" name="userRole" id="admin_role" />
              <label for="admin_role">ADMIN</label>
            </div>
          </div>
          <input type="submit" class="submit_button" value="Enter" @click="submitForm" />
          <router-link to="/home" class="guest_button">Enter as guest</router-link>
          <router-link to="/signup" style="margin-top: 1vh; font-size: 2vh">Create Account</router-link>
        </form>
        <p>{{ message }}</p>
      </div>
    </div>
  </div>
</template>
In this updated code, the radio inputs have the name="userRole" attribute, which groups them together. Now, only one radio button can be selected within the "userRole" group.








<script >
import Router from "../router.ts";
import axios from "axios";
import {email} from "@vuelidate/validators";

export default {
  name: "SignUpPage",
  // login: '',
  // password: '',
  // role: '',
  // userId: '',
  // message: '',
  data () {
    return {
      role: '',
      message: '',
      login: '',
      password: '',
    }
  },
  methods: {
    getCheckedRole() {
      let chosenRole = '';
      if (document.getElementById('student_role').checked) {
        console.log("STUD")
        chosenRole = 'STUDENT'
      } else {
        if (document.getElementById('teacher_role').checked) {
          console.log("TICHA")
          chosenRole = 'TEACHER'
        } else {
          if (document.getElementById('admin_role').checked) {
            console.log("ADMIN")
            chosenRole = 'ADMIN'
          }
        }
      }
      return chosenRole;
    },
    createAccount() {
      this.message = "";
      this.login = "";
      this.password = "";
      Router.push('/signup')
    },
    enterAsGuest() {
      Router.push('/')
    },
    submitForm(e){
      e.preventDefault()
      const body = {
        login: this.login,
        password: this.password,
        role: this.getCheckedRole(),
      }
      axios.post('http://localhost:8081/auth/signin', body, {
      })
          .then(response => {
            if (response.status === 200) {
              this.showError = false;
              this.message = 'Login accepted';
              this.login = "";
              this.password = "";
              this.role = null;
              localStorage.removeItem("userId")
              localStorage.setItem("userId", response.data)
              Router.push('/home');
            } else {
              if(localStorage.getItem("userId") <= 0){
                localStorage.setItem("userId", 5);
              }
            }
          })
          .catch(error => {
            Router.push('/home');
            console.error('Error:', error);
          });
    }
  }
}
</script>

<style>
@import "./style/general_styles.css";
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  margin-top: 13vh;
  font-size: 90%;
}

/* Dark theme */
body.dark-theme {
  background-color: #5b2a2a;
  color: white;
}

/* Light theme */
body.light-theme {
  background-color: white;
  color: black;
}

/* Dark theme */
body.dark-theme {
  background-color: #333;
  color: whitesmoke;
}

/* Light theme */
body.light-theme {
  background-color: white;
  color: #1a1a1a;
}
/* Dark theme */
[data-theme="dark"] body {
  background-color: #333;
  color: white;
}

/* Light theme */
[data-theme="light"] body {
  background-color: white;
  color: black;
}

#form-post{
  display: flex;
  flex-direction: column;
  align-items: center;
}

textarea{
  width:fit-content;
  margin:5px auto;
  resize:none;
  padding:0.4rem;
  border-radius: 6px;
  box-shadow: 5px 5px 15px #ffffff;
}
:placeholder{
  font-family:arial, sans-serif;
}

</style>