# Use a Node.js image as the base
# Copy only the build output to a new stage
FROM nginx:alpine-slim

COPY index.html /usr/share/nginx/html

# Cấu hình Nginx tối ưu
COPY /nginx/default.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
