<template>
  <div class="admin-panel" onload="loadContent()">
    <div class="admin-panel-grid">
      <router-link to="/admin" class="admin-panel-item">
        <TotalRequests class="total_req"/>
      </router-link>
      <router-link to="/admin/all_admins" class="admin-panel-item">
        <h2>All Admins</h2>
      </router-link>
      <router-link to="/students" class="admin-panel-item">
        <h2>All Students</h2>
      </router-link>
      <router-link to="/admin/teachers" class="admin-panel-item">
        <h2>All Teachers</h2>
      </router-link>
      <router-link to="/admin/skill_types" class="admin-panel-item">
        <h2>All Skill Types</h2>
      </router-link>
      <router-link to="/admin/assignment-types" class="admin-panel-item">
        <h2>All Assignment Types</h2>
      </router-link>
      <router-link to="/admin/student_groups" class="admin-panel-item">
        <h2>All Groups</h2>
      </router-link>
      <router-link to="/admin/negotiations" class="admin-panel-item">
        <h2>Assignment Negotiations</h2>
      </router-link>
      <router-link to="/admin/schedule-config" class="admin-panel-item">
        <h2>Student Schedule Config</h2>
      </router-link>
      <button @click="goToEurekaPage()"  class="admin-panel-item">
        <h2>Servers Instances Status</h2>
      </button>
      <button @click="goToPrometheus()"  class="admin-panel-item">
        <h2>Server Stats</h2>
      </button>
      <button @click="goToGatewayStats()"  class="admin-panel-item">
        <h2>All Available Service Stats</h2>
      </button>

    </div>
  </div>
</template>

<script lang="ts">
import TotalRequests from "./TotalRequests.vue";
export default {

  name: 'AdminPanel',
  components: {
    TotalRequests
  },
  methods: {
    loadContent() {
      window.location.reload()
    },
    goToEurekaPage() {
      window.open('http://localhost:8761', '_blank');
    },
    goToPrometheus() {
      window.open('http://localhost:8090/actuator/prometheus', '_blank');
    },
    goToGatewayStats() {
      window.open('http://localhost:8090/actuator', '_blank');
    }
  }
};
</script>

<style>
@import "./style/common_table.css";

.admin-panel-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 20px;
}

.admin-panel-item {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #bfbed7;
  border: 1px solid #330b0b;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  transition: transform 0.3s;
  box-shadow: 0px 8px 11px -2px rgba(0, 0, 0, 0.5);
}

.admin-panel-item:hover {
  transform: translateY(-5px);
  box-shadow: 0px 5px 20px rgba(0, 0, 0, 0.1);
}
</style>