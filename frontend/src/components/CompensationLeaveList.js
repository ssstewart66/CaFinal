import React from 'react';
import CompensationItem from './CompensationLeaveItem';

const CompensationLeaveList = ({ leaves, onUpdateStatus }) => {
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
                <CompensationItem
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
