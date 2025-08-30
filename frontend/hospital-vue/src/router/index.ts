import { createRouter, createWebHistory } from 'vue-router';
import DoctorDashboard from '../views/DoctorDashboard.vue';
import NurseDashboard from '../views/NurseDashboard.vue';
import FamilyDashboard from '../views/FamilyDashboard.vue';
import FinanceDashboard from '../views/FinanceDashboard.vue';
import AdminDashboard from '../views/AdminDashboard.vue';
import Login from '../views/Login.vue';
import BedAssignment from '../views/BedAssignment.vue';
import WardManagement from '../views/WardManagement.vue';

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'login', component: Login },
  { path: '/admin', name: 'admin', component: AdminDashboard },
  { path: '/doctor', name: 'doctor', component: DoctorDashboard },
  { path: '/nurse', name: 'nurse', component: NurseDashboard, children: [
    { path: 'bed-assignment', name: 'bedAssignment', component: BedAssignment },
    { path: 'ward-management', name: 'wardManagement', component: WardManagement }
  ] },
  { path: '/family', component: FamilyDashboard },
  { path: '/finance', component: FinanceDashboard },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router; 