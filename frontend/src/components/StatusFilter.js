import React from 'react';

const StatusFilter = ({ status, setStatus }) => {
    return (
        <div>
            <label>
                Filter by Status:
                <select value={status} onChange={(e) => setStatus(e.target.value)}>
                    <option value="APPLIED">Pending</option>
                    <option value="APPROVED">Approved</option>
                    <option value="REJECTED">Rejected</option>
                    <option value="CANCEL">Cancel</option>
                    <option value="UPDATED">Updated</option>
                    <option value="DELETED">Delete</option>
                </select>
            </label>
        </div>
    );
};

export default StatusFilter;