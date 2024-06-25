import React from 'react';

const CompensationLeaveItem = ({ application, onUpdateStatus }) => {
    const handleStatusChange = (newStatus) => {
        onUpdateStatus(application.id, newStatus);
    };

    return (
        <tr>
            <td>{application.id}</td>
            <td>{application.startDate}</td>
            <td>{application.endDate}</td>
            <td>{application.contact_details}</td>
            <td>{application.work_dissemination}</td>
            <td>{application.reason}</td>
            <td>{application.status}</td>
            <td>
                <button onClick={() => handleStatusChange('APPROVED')}>Approve</button>
                <button onClick={() => handleStatusChange('REJECTED')}>Reject</button>
            </td>
        </tr>
    );
};

export default CompensationLeaveItem;
