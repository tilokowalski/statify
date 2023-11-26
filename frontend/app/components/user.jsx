
'use client';

import { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import Logout from '@mui/icons-material/Logout';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';

export default function UserMenu({ userData, onLogout }) {
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = async () => {
        // TODO Logout
        handleClose();
    };

    const avatarUrl = userData.images && userData.images[0] ? userData.images[0].url : null;

    return (
        <div>
            <Button
                id="user-menu-button"
                aria-controls={open ? 'user-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
                endIcon={<ArrowDropDownIcon />}
            >
                <Avatar src={avatarUrl} sx={{ width: 32, height: 32, marginRight: 2 }} />
                {userData.display_name}
            </Button>
            <Menu
                id="user-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'user-menu-button',
                }}
            >
              <MenuItem onClick={handleLogout}>
                  <ListItemIcon>
                      <Logout fontSize="small" />
                  </ListItemIcon>
                  Logout
              </MenuItem>
            </Menu>
        </div>
    );
}
