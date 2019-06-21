package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Models a user's blog entry containing a list of comments.
 */
@Entity
@Table(name = "blog_entries")
public class BlogEntry {

	/**
	 * The maximum length of this entry's title.
	 */
	public static final int TITLE_LENGTH = 200;

	/**
	 * The maximum length of this entry's text content.
	 */
	public static final int TEXT_LENGTH = 4096;

	/**
	 * The ID of this entry.
	 */
	private Long id;

	/**
	 * A list of comments for this entry.
	 */
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * This entry's date of creation.
	 */
	private Date createdAt;

	/**
	 * This entry's last date of modification.
	 */
	private Date lastModifiedAt;

	/**
	 * The title of this entry.
	 */
	private String title;

	/**
	 * The text of this entry.
	 */
	private String text;

	/**
	 * The user who created this entry.
	 */
	private BlogUser creator;

	/**
	 * Returns the ID of this entry.
	 *
	 * @return the ID of this entry
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of this entry to the given value.
	 *
	 * @param id the ID to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns a list of comments for this entry.
	 *
	 * @return a list of comments for this entry
	 */
	@OrderBy("postedOn")
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the list of comments of this entry to the given value.
	 *
	 * @param comments the list of comments to set
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns this entry's date of creation.
	 *
	 * @return this entry's date of creation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets this entry's date of creation to the given value.
	 *
	 * @param createdAt the date of creation to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns this entry's last date of modification.
	 *
	 * @return this entry's last date of modification
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets this entry's last date of modification to the given value.
	 *
	 * @param lastModifiedAt the last date of modification to set
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns the title of this entry.
	 *
	 * @return the title of this entry
	 */
	@Column(length = TITLE_LENGTH, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this entry to the given value.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the text of this entry.
	 *
	 * @return the text of this entry
	 */
	@Column(length = TEXT_LENGTH, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this entry to the given value.
	 *
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the user who created this entry.
	 *
	 * @return the user who created this entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets the user who created this entry to the given value.
	 *
	 * @param creator the user to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}
}