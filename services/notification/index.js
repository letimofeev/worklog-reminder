const express = require('express')
const path = require("path");

const ENV_FILE = path.join(__dirname, '.env');
require('dotenv').config({path: ENV_FILE});

const PORT = process.env.port || process.env.PORT || 8200

const app = express()

app.listen(PORT, () => console.log(`\nServer started on port ${PORT}`))
