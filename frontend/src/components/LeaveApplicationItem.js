import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LeaveApplicationItem = ({ application, onUpdateStatus }) => {

    const [username, setUsername] = useState('未知');

    useEffect(() => {
        const fetchUsername = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/manager/user/${application.userId}`);
                setUsername(response.data.username);
            } catch (error) {
                console.error('获取用户名时出错:', error);
            }
        };

        if (application.userId) {
            fetchUsername();
        }
    }, [application.userId]);

    const handleStatusChange = (newStatus) => {
        onUpdateStatus(application.id, newStatus);
    };

    return (
        <tr>
            <td>{application.id}</td>
            <td>{username}</td>
            <td>{application.leaveType ? application.leaveType.name : '未知'}</td>
            <td>{application.start_date}</td>
            <td>{application.end_date}</td>
            <td>{application.reason}</td>
            <td>{application.work_dissemination}</td>
            <td>{application.contact_details}</td>
            <td>{application.status}</td>
            <td>{new Date(application.created_at).toLocaleString()}</td>
            <td>{new Date(application.updated_at).toLocaleString()}</td>
            <td>
                {application.status === 'APPLIED' && (
                    <>
                        <button onClick={() => handleStatusChange('APPROVED')}>Approve</button>
                        <button onClick={() => handleStatusChange('REJECTED')}>Reject</button>
                    </>
                )}
                {application.status === 'APPROVED' && (
                    <button onClick={() => handleStatusChange('REJECTED')}>Reject</button>
                )}
                {application.status === 'REJECTED' && (
                    <button onClick={() => handleStatusChange('APPROVED')}>Approve</button>
                )}
            </td>
        </tr>
    );
};

export default LeaveApplicationItem;