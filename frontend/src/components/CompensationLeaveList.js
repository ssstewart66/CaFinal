import React from 'react';
import CompensationLeaveItem from "./CompensationLeaveItem";

const CompensationLeaveList = ({ leaves, onUpdateStatus }) => {
    console.log('CompensationLeaveList received leaves:', leaves);

    if (!leaves || leaves.length === 0) {
        return <p>No compensation leaves found.</p>;
    }
    return (
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Contact</th>
                <th>Work Dissemination</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {leaves.map(application => (
                <CompensationLeaveItem
                    key={application.id}
                    application={application}
                    onUpdateStatus={onUpdateStatus}
                />
            ))}
            </tbody>
        </table>
    );
}

export default CompensationLeaveList;
