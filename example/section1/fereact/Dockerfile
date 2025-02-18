# Use a Node.js image as the base
FROM node:lts-slim AS build

ARG REACT_APP_HOST_IP_ADDRESS
# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json for faster build
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

ENV REACT_APP_HOST_IP_ADDRESS $REACT_APP_HOST_IP_ADDRESS
# Build the React application
RUN npm run build

# Copy only the build output to a new stage
FROM nginx:alpine-slim

COPY --from=build /app/build /usr/share/nginx/html

# Cấu hình Nginx tối ưu
COPY /nginx/default.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
