import React, { useState } from 'react';
import LeaveApplicationItem from './LeaveApplicationItem';

const LeaveApplicationList = ({ applications, onUpdateStatus }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(2); // Default items per page
    const totalPages = Math.ceil(applications.length / itemsPerPage);

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value));
        setCurrentPage(1); // Reset to first page when items per page changes
    };

    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentApplications = applications.slice(startIndex, startIndex + itemsPerPage);

    return (
        <div>
            <table>
                <thead>
                <tr>
                    <th>Leave Type</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Reason</th>
                    <th>Work Dissemination</th>
                    <th>Contact Details</th>
                    <th>Status</th>
                    <th>Create Time</th>
                    <th>Update Time</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {currentApplications.map(application => (
                    <LeaveApplicationItem
                        key={application.id}
                        application={application}
                        onUpdateStatus={onUpdateStatus}
                    />
                ))}
                </tbody>
            </table>

            <div className="pagination">
                <button
                    disabled={currentPage === 1}
                    onClick={() => handlePageChange(currentPage - 1)}>
                    &laquo;
                </button>
                {Array.from({ length: totalPages }, (_, i) => (
                    <button
                        key={i + 1}
                        className={currentPage === i + 1 ? 'active' : ''}
                        onClick={() => handlePageChange(i + 1)}
                    >
                        {i + 1}
                    </button>
                ))}
                <button
                    disabled={currentPage === totalPages}
                    onClick={() => handlePageChange(currentPage + 1)}>
                    &raquo;
                </button>
            </div>

            <div>
                <label>Results per page: </label>
                <select value={itemsPerPage} onChange={handleItemsPerPageChange}>
                    <option value={1}>1</option>
                    <option value={2}>2</option>
                    <option value={5}>5</option>
                    <option value={10}>10</option>
                </select>
            </div>
        </div>
    );
};

export default LeaveApplicationList;