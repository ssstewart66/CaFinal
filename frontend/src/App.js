import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Route, Routes, Link } from 'react-router-dom';
import LeaveApplicationList from './components/LeaveApplicationList';
import StatusFilter from './components/StatusFilter';
import './App.css';
import CompensationLeaveList from "./components/CompensationLeaveList";
import { useLocation } from 'react-router-dom';
import ReportGenerator from "./components/ReportGenerator";


function useQuery() {
  return new URLSearchParams(useLocation().search);
}

function App() {
  const [leaveApplications, setLeaveApplications] = useState([]);
  const [compensationLeaves, setCompensationLeaves] = useState([]);
  const [status, setStatus] = useState('APPLIED');
  const [error, setError] = useState(null);
  const query = useQuery();
  const username = query.get("username");
  const managerId = query.get("id");

  useEffect(() => {
    const fetchLeaveApplications = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/manager/status/${status}/approved/${managerId}`);
        //const response = await axios.get(`http://localhost:8080/manager/status/${status}`);
       if (Array.isArray(response.data)) {
          setLeaveApplications(response.data);
        } else {
          console.error('数据', response.data);
          setError('数据格式不正确');
        }
      } catch (error) {
        setError('获取请假申请时出错');
        console.error('获取请假申请时出错:', error);
      }
    };

    const fetchCompensationLeaves = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/manager/compensation/status/${status}`);
          setCompensationLeaves(response.data);
      } catch (error) {
        setError('获取补偿假期申请时出错');
        console.error('获取补偿假期申请时出错:', error);
      }
    };

    fetchLeaveApplications();
    fetchCompensationLeaves();
  }, [status, managerId]);

  const updateLeaveApplicationStatus = async (id, newStatus) => {
    try {
      const updatedApplication = { status: newStatus }; // 创建一个包含新状态的对象
      await axios.put(`http://localhost:8080/manager/${id}/status`, updatedApplication);
      // 更新后重新获取数据
      const response = await axios.get(`http://localhost:8080/manager/status/${status}`);
      setLeaveApplications(response.data);
    } catch (error) {
      setError('更新请假申请状态时出错');
      console.error('更新请假申请状态时出错:', error);
    }
  };

  const updateCompensationLeaveStatus = async (id, newStatus) => {
    try {
      const updatedApplication = { status: newStatus };
      await axios.put(`http://localhost:8080/manager/compensation/${id}/status`, updatedApplication);
      const response = await axios.get(`http://localhost:8080/manager/compensation/status/${status}`);
      setCompensationLeaves(response.data);
    } catch (error) {
      setError('更新补偿假期状态时出错');
      console.error('更新补偿假期状态时出错:', error);
    }
  };

  const handleLogout = () => {
    window.location.href = 'http://localhost:8080/login';
  };

  const handleSettingsClick = () => {
    window.location.href = 'http://localhost:8080/staff/leaveApplication/history';
  };

  const chartData = {
    labels: ['Annual Leave', 'Compensation Leave', 'Medical Leave', 'Other Leave'],
    datasets: [{
      data: [
        leaveApplications.filter(app => app.leaveType.name === 'Annual_Leave').length,
        leaveApplications.filter(app => app.leaveType.name === 'Compensation_Leave').length,
        leaveApplications.filter(app => app.leaveType.name === 'Medical_Leave').length,
        leaveApplications.filter(app => !['Annual_Leave', 'Compensation_Leave', 'Medical_Leave'].includes(app.leaveType.name)).length,
      ],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
    }]
  };

  return (
      <div className="App">
        <div className="sidebar">
          <h2>Menu</h2>
          <ul>
            {username && <h2>Hello, {username} </h2>}
            <li><Link to={`/?username=${username}&id=${managerId}`}>Home</Link></li>
            <li><Link to={`/applications?username=${username}&id=${managerId}`}>Leave Application Management</Link></li>
            <li><Link to={`/compensation-leaves?username=${username}&id=${managerId}`}>Compensation Leave
              Management</Link></li>
            <li><a href="#" onClick={handleSettingsClick}>Leave Application</a></li>
            <li><Link to={`reports?username=${username}&id=${managerId}`}>Generate Reports</Link> </li>
            <li>
              <button onClick={handleLogout}>Logout</button>
            </li>
          </ul>
        </div>
        <div className="main-content">

          <Routes>
            <Route path="/" element={<h1>Home</h1>} />
            <Route path="/applications" element={
              <>
                <h1>Leave Application Management</h1>
                <StatusFilter status={status} setStatus={setStatus} />
                {error && <p>{error}</p>}
                <LeaveApplicationList applications={leaveApplications} onUpdateStatus={updateLeaveApplicationStatus} />
              </>
            } />
            <Route path="/compensation-leaves" element={
              <>
                <h1>Compensation Leave Management</h1>
                <StatusFilter status={status} setStatus={setStatus} />
                {error && <p>{error}</p>}
                <CompensationLeaveList leaves={compensationLeaves} onUpdateStatus={updateCompensationLeaveStatus} />
              </>
            } />
            <Route path="/settings" element={<></>} />
            <Route path="/reports" element={<ReportGenerator />}/>
          </Routes>
        </div>
      </div>
  );
}

export default App;
